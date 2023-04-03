package com.example.seckill.service.impl;

import com.alibaba.nacos.common.utils.MD5Utils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.commonutils.DateTimeUtil;
import com.example.commonutils.RedisUtil;
import com.example.commonutils.ResultCode;
import com.example.commonutils.vo.SeckillCourseOrder;
import com.example.seckill.config.RabbitBeanConfig;
import com.example.seckill.entity.SeckillCourse;
import com.example.seckill.mapper.SeckillCourseMapper;
import com.example.seckill.service.SeckillCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.seckill.service.SeckillSessionService;
import com.example.seckill.util.CorrelationDataUtil;
import com.example.servicebase.exception.GlobalException;
import com.example.servicebase.shiro.utils.ShiroUtil;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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


    //秒杀场次前缀 完整的key为前缀+场次id
    private static final String SECKILL_SESSION_PREFIX = "seckill::session";

    //某个商品的库存信息 完整的key为前缀+商品id
    private static final String SECKILL_COURSE_STORE_PREFIX = "seckill::store";
    //秒杀场次开始时间 完整的key为前缀+商品id
    private static final String SECKILL_COUSE_START_TIME_PREFIX = "seckill::startTime";
    private static final String SECKILL_Buyer_Set_PREFIX = "seckill::buyerSet";
    //盐
    private static final String SALT = "hgka$syf83wlfnZ.,.221hksd";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    SeckillSessionService seckillSessionService;

    @Override
    public void shelf() {
        LocalDateTime now = LocalDateTime.now();
        shelf(now);
    }

    @Override
    public void shelf(LocalDateTime now) {
        //先上架秒杀场次信息
        seckillSessionService.shelf(now);
        //查询最近三天所有参与秒杀的商品
        LambdaQueryWrapper<SeckillCourse> wrapper = new LambdaQueryWrapper<>();
        LocalDateTime ddl = now.plusDays(3);
        wrapper.ge(SeckillCourse::getStartTime, now);
        wrapper.le(SeckillCourse::getStartTime, ddl);
        //按场次排序
        wrapper.orderByAsc(SeckillCourse::getStartTime);
        List<SeckillCourse> secKillCourses = baseMapper.selectList(wrapper);

        //上架
        for (SeckillCourse course : secKillCourses) {
            //上架到对应的场次,如果已经存在就跳过
            Integer sessionId = course.getSeckillSessionId();
            String sessionKey = RedisUtil.buildKey(SECKILL_SESSION_PREFIX, sessionId);

            //不存在时才会上架
            if (!redisTemplate.opsForHash().hasKey(sessionKey, course.getSeckillId())) {
                //新增课程信息
                redisTemplate.opsForHash().put(sessionKey, course.getSeckillId(), course);
                //四天后自动删除,冗余一天
                redisTemplate.expire(sessionKey, 4, TimeUnit.DAYS);

                //库存key为前缀+加密后的课程id,value为库存数量
                String storeKey = getStoreKey(course.getSeckillId());
                //初始化课程库存信号量
                RSemaphore semaphore = redissonClient.getSemaphore(storeKey);
                semaphore.trySetPermits(course.getStoreCount());
                redisTemplate.expire(storeKey, 4, TimeUnit.DAYS);

                //冗余一个key保存课程的开始时间,也可以不冗余,从hash里面取出课程信息,然后反序列化,再获取开始时间
                String startTimeKey = RedisUtil.buildKey(SECKILL_COUSE_START_TIME_PREFIX, course.getSeckillId().toString());
                redisTemplate.opsForValue().set(startTimeKey, course.getStartTime());
                redisTemplate.expire(startTimeKey, 4, TimeUnit.DAYS);

                //初始化买家名单
                String buySetKey = getBuyerSetKey(course.getSeckillId());
                redisTemplate.opsForSet().add(buySetKey, "");
                redisTemplate.expire(storeKey, 4, TimeUnit.DAYS);
            }
        }
    }


    @Override
    public String getSeckillCourseKey(String seckillId) {
        //开始时间
        String startTimeKey = RedisUtil.buildKey(SECKILL_COUSE_START_TIME_PREFIX, seckillId);
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
    public List<SeckillCourse> getSeckillInfo(Integer seckillSessionId) {
        String seckillSessionKey = RedisUtil.buildKey(SECKILL_SESSION_PREFIX, seckillSessionId);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(seckillSessionKey);
        List<SeckillCourse> res = new ArrayList<>();
        entries.forEach((k, v) -> {
            SeckillCourse course = (SeckillCourse) v;
            res.add(course);
        });
        return res;
    }

    @Override
    public void doSeckill(Integer sessionId, Integer seckillId, String seckillKey) {
        //说明:为了方便测试 用户id临时生成
//        String userId = String.valueOf((int) (1 + Math.random() * 10));
        String userId = ShiroUtil.getUserId();
        String buyerSetKey = getBuyerSetKey(seckillId);
        boolean hasAddedToUserSet = false; //是否已经添加到重复购买列表
        boolean hasDeductionStore = false; //是否已扣减库存
        RSemaphore storeSemaphore = null;

        try {
            //秒杀key有效性检验
            if (!redisTemplate.hasKey(buyerSetKey)) {
                throw new GlobalException(ResultCode.ERROR, "秒杀key非法!");
            }
            //重复购买校验
            if (redisTemplate.opsForSet().add(buyerSetKey, userId) != 1) {
                throw new GlobalException(ResultCode.ERROR, "请勿重复购买!");
            }
            hasAddedToUserSet = true;
            //商品库存校验
            String storeKey = getStoreKey(seckillId);
            storeSemaphore = redissonClient.getSemaphore(storeKey);
            try {
                //100ms算是很高的延迟了,以redis单机十万的qps,100ms能进行1万次信号量-1,100ms了还没等到只能说明信号量为0了
                if (!storeSemaphore.tryAcquire(
                        100, TimeUnit.MICROSECONDS)) {
                    throw new GlobalException(ResultCode.ERROR, "商品已售空!");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            hasDeductionStore = true;
            //生成订单信息
            String sessionKey = RedisUtil.buildKey(SECKILL_SESSION_PREFIX, sessionId);
            SeckillCourse course = (SeckillCourse) redisTemplate.opsForHash().get(sessionKey, seckillId);

            SeckillCourseOrder order = createSeckillCourseOrder(course, userId);
            //投递到消息队列
            rabbitTemplate.convertAndSend(RabbitBeanConfig.SECKILL_ORDER_SAVE_EXCHANGE, RabbitBeanConfig.SECKILL_ORDER_SAVE_BINDING,
                    order, CorrelationDataUtil.generate());
        } catch (Exception e) {
            if (e instanceof GlobalException) {
                //自己手动抛的不管
                throw e;
            }
            //回滚!redis不支持只能手动来
            if (hasAddedToUserSet) {
                redisTemplate.opsForSet().remove(buyerSetKey, userId);
            }
            if (hasDeductionStore) {
                storeSemaphore.release();
            }
            throw e;
        }
    }


    private String getStoreKey(Integer seckillId) {
        String encodedSeckillId = MD5Utils.md5Hex(seckillId + SALT, "utf-8");
        String storeKey = RedisUtil.buildKey(SECKILL_COURSE_STORE_PREFIX, encodedSeckillId);
        return storeKey;
    }

    private String getBuyerSetKey(Integer seckillId) {
        String encodedSeckillId = MD5Utils.md5Hex(seckillId + SALT, "utf-8");
        return RedisUtil.buildKey(SECKILL_Buyer_Set_PREFIX, encodedSeckillId);
    }

    public SeckillCourseOrder createSeckillCourseOrder(SeckillCourse seckillCourse, String userId) {
        SeckillCourseOrder order = new SeckillCourseOrder();
        BeanUtils.copyProperties(seckillCourse, order);
        order.setBuyerId(userId);
        order.setActualPrice(seckillCourse.getCurrentPrice());
        return order;
    }

}
