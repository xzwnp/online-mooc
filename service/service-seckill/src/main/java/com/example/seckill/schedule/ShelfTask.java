package com.example.seckill.schedule;

import com.example.seckill.service.SeckillCourseService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * com.example.seckill.schedule
 *
 * @author xiaozhiwei
 * 2023/3/15
 * 15:09
 */
@Component
@Slf4j
public class ShelfTask {
    private static final String SECKILL_SHELF_LOCK_NAME = "seckill::shelf::lock";
    @Autowired
    SeckillCourseService seckillCourseService;
    @Autowired
    RedissonClient redissonClient;

    @Scheduled(cron = "0 0 5 * *  ?") //生产阶段实际使用
//    @Scheduled(cron = "*/3  *  *  *  *  ?") //开发阶段测试用
    public void scheduleShelf() {
        //保证只有一个服务在执行定时任务,避免分布式环境下的冲突
        //过时 已把数据类型从list换成set,重复上架无影响
//		RLock lock = redissonClient.getLock(SECKILL_SHELF_LOCK_NAME);
//		try {
//			if (lock.tryLock(10, TimeUnit.SECONDS)) {
//				log.info("正在执行秒杀上架任务...");
//                seckillCourseService.shelf();
//				try {
//				} finally {
//					lock.unlock();
//				}
//			}
//		} catch (InterruptedException ignored) {
//			log.info("没有抢到定时任务锁");
//		}
        log.info("正在执行秒杀上架任务...");
        seckillCourseService.shelf();
        log.info("完毕");

    }
}
