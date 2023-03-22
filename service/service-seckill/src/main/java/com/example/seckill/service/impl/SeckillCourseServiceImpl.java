package com.example.seckill.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.commonutils.DateTimeUtil;
import com.example.commonutils.JsonUtil;
import com.example.commonutils.RedisUtil;
import com.example.commonutils.ResultCode;
import com.example.seckill.config.RabbitBeanConfig;
import com.example.seckill.entity.SeckillCourse;
import com.example.seckill.entity.SeckillCourseOrder;
import com.example.seckill.mapper.SeckillCourseMapper;
import com.example.seckill.service.SeckillCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.servicebase.exception.GlobalException;
import com.example.servicebase.shiro.utils.ShiroUtil;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-15
 */
@Service
public class SeckillCourseServiceImpl extends ServiceImpl<SeckillCourseMapper, SeckillCourse> implements SeckillCourseService {
	private final String SECKILL_INFO_KEY = "seckill::info";
	private final String SECKILL_STORE_PREFIX = "seckill::store";
	private final String SECKILL_START_TIME_PREFIX = "seckill::startTime";
	private final String SECKILL_Buyer_Set_PREFIX = "seckill::buyerSet";
	private final String SALT = "hgka$syf83wlfnZ.,.221hksd";


	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Autowired
	RedissonClient redissonClient;


	@Autowired
	RabbitTemplate rabbitTemplate;

	@Override
	public void shelf() {
		//查询最近三天所有参与秒杀的商品
		LambdaQueryWrapper<SeckillCourse> wrapper = new LambdaQueryWrapper<>();
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime ddl = now.plusDays(3);
		wrapper.ge(SeckillCourse::getStartTime, now);
		wrapper.le(SeckillCourse::getStartTime, ddl);
		//按场次排序
		wrapper.orderByAsc(SeckillCourse::getStartTime);
		List<SeckillCourse> secKillCourses = baseMapper.selectList(wrapper);
		for (SeckillCourse course : secKillCourses) {
			//上架到对应的场次,如果已经存在就跳过
			LocalDateTime startTime = course.getStartTime();
			//不存在时才会上架
			if (!redisTemplate.opsForHash().hasKey(SECKILL_INFO_KEY, course.getSeckillId())) {
				//新增课程信息
				redisTemplate.opsForHash().put(SECKILL_INFO_KEY, course.getSeckillId(), course);
				//课程id加密后,把库存数量设置为信号量的值
				String encodedCourseId = MD5Utils.md5Hex(course.getSeckillId() + SALT, "utf-8");
				String storeKey = RedisUtil.buildKey(SECKILL_STORE_PREFIX, encodedCourseId);
				RSemaphore semaphore = redissonClient.getSemaphore(storeKey);
				semaphore.trySetPermits(course.getStoreCount());
				//开始时间,新增
				String startTimeKey = RedisUtil.buildKey(SECKILL_START_TIME_PREFIX, course.getSeckillId().toString());
				redisTemplate.opsForValue().set(startTimeKey, course.getStartTime());
			}
		}
	}

	@Override
	public String getSeckillCourseKey(String seckillId) {
		//开始时间
		String startTimeKey = RedisUtil.buildKey(SECKILL_START_TIME_PREFIX, seckillId);
		String startTimeString = String.valueOf(redisTemplate.opsForValue().get(startTimeKey));
		LocalDateTime startTime = DateTimeUtil.parseDate(startTimeString);
		//检查是否已经开始
		if (LocalDateTime.now().isBefore(startTime)) {
			throw new GlobalException(ResultCode.ERROR, "秒杀还未开始!");
		}
		//获取课程id
		String encodedSeckillId = MD5Utils.md5Hex(seckillId + SALT, "utf-8");
		return encodedSeckillId;
	}

	@Override
	public List<SeckillCourse> getSeckillInfo() {
		Map<Object, Object> entries = redisTemplate.opsForHash().entries(SECKILL_INFO_KEY);
		List<SeckillCourse> res = new ArrayList<>();
		entries.forEach((k, v) -> {
			SeckillCourse course = JsonUtil.parse(String.valueOf(v), SeckillCourse.class);
			res.add(course);
		});
		return res;
	}

	@Override
	public void doSeckill(Integer seckillId, String seckillKey) {
		String userId = ShiroUtil.getUserId();
		//重复购买校验
		String encodedSeckillId = MD5Utils.md5Hex(seckillId + SALT, "utf-8");
		if (redisTemplate.opsForSet().add(encodedSeckillId, userId) != 1) {
			throw new GlobalException(ResultCode.ERROR, "请勿重复购买!");
		}
		//商品库存校验
		RSemaphore semaphore = redissonClient.getSemaphore(encodedSeckillId);
		try {
			//100ms算是很高的延迟了,以redis单机十万的qps,100ms能进行1万次信号量-1,100ms了还没等到只能说明信号量为0了
			if (!semaphore.tryAcquire(
				100, TimeUnit.MICROSECONDS)) {
				throw new GlobalException(ResultCode.ERROR, "商品已售空!");
			}
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		Object o = redisTemplate.opsForHash().get(SECKILL_INFO_KEY, seckillId);
		SeckillCourse course = JsonUtil.parse(String.valueOf(o), SeckillCourse.class);

		SeckillCourseOrder order = new SeckillCourseOrder(course, userId, LocalDateTime.now(), course.getCurrentPrice());
		//投递到消息队列
		rabbitTemplate.convertAndSend(RabbitBeanConfig.SECKILL_ORDER_SAVE_EXCHANGE, RabbitBeanConfig.SECKILL_ORDER_SAVE_BINDING, order);
	}

	/**
	 * 删除过期的课程
	 * redis不支持给hash类型的某个key设置过期时间
	 * todo
	 */
	@Override
	public void clearExpireSeckillCourse() {

	}
}
