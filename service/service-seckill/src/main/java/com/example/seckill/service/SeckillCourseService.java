package com.example.seckill.service;

import com.example.seckill.entity.SeckillCourse;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface SeckillCourseService extends IService<SeckillCourse> {
    /**
     * 秒杀课程上架
     */
    void shelf();

    void shelf(LocalDateTime now);

    /**
     * 获取某个秒杀课程的秒杀key
     * 这么做是为了防止秒杀未开始,而秒杀接口暴露
     * 比如某个脚本11:59:50发的请求,12:00:00刚好到达服务器,就能秒杀到
     * 而用户12:00:00才能发请求,12:00:10才到达服务器,抢不过脚本,这是不公平的!
     */
    String getSeckillCourseKey(String sessionId, String seckillId);

    /**
     * 获取某一秒杀场次的所有课程
     */
    List<SeckillCourse> getSeckillInfo(Integer seckillSessionId);

    /**
     * 进行秒杀
     */
    void doSeckill(Integer sessionId, Integer seckillId, String key);


}
