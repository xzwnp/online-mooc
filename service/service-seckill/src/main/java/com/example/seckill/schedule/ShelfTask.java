package com.example.seckill.schedule;

import com.example.seckill.service.SeckillCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * com.example.seckill.schedule
 *
 * @author xiaozhiwei
 * 2023/3/15
 * 15:09
 */
@Component
public class ShelfTask {
    @Autowired
    SeckillCourseService seckillCourseService;

    @Scheduled(cron = "0 0 5 * * *")
    public void scheduleShelf() {

    }
}
