package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.commonutils.JsonUtil;
import com.example.seckill.entity.SeckillSession;
import com.example.seckill.mapper.SeckillSessionMapper;
import com.example.seckill.service.SeckillSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-15
 */
@Service
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionMapper, SeckillSession> implements SeckillSessionService {
    //全部秒杀场次的信息
    private static final String SECKILL_ALL_SESSION_KEY = "seckill::session::all";
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 上架最近三天的秒杀场次
     */
    @Override
    public void shelf() {
        shelf(LocalDateTime.now());
    }

    @Override
    public void shelf(LocalDateTime now) {
        clearExpireSeckillCourse(now);
        LocalDateTime future = now.plusDays(3);
        LambdaQueryWrapper<SeckillSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(SeckillSession::getStartTime, now);
        wrapper.le(SeckillSession::getStartTime, future);
        List<SeckillSession> sessions = list(wrapper);
        for (SeckillSession session : sessions) {
            redisTemplate.opsForSet().add(SECKILL_ALL_SESSION_KEY, session);
        }
    }

    @Override
    public List<SeckillSession> getCurrentSeckillSessions() {
        Set<Object> members = redisTemplate.opsForSet().members(SECKILL_ALL_SESSION_KEY);
        assert members != null;
        return members.stream().
                map((member) -> (SeckillSession) member)
                .collect(Collectors.toList());
    }

    /**
     * 删除过期的秒杀场次
     * 查询所有时间秒杀时间为以前的场次,然后删除
     * todo
     */
    public void clearExpireSeckillCourse(LocalDateTime now) {
        LambdaQueryWrapper<SeckillSession> wrapper = new LambdaQueryWrapper<>();
        wrapper.le(SeckillSession::getEndTime, now);
        List<SeckillSession> sessions = list(wrapper);
        for (SeckillSession session : sessions) {
            redisTemplate.opsForSet().remove(SECKILL_ALL_SESSION_KEY, session);
        }
    }
}