package com.example.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.seckill.entity.SeckillCourse;
import com.example.seckill.entity.SeckillSession;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-15
 */
public interface SeckillSessionService extends IService<SeckillSession> {
    void shelf();

    void shelf(LocalDateTime startTime);

    List<SeckillSession> getCurrentSeckillSessions();
}
