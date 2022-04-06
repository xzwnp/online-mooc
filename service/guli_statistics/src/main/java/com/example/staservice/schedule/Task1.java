package com.example.staservice.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * com.example.staservice.schedule
 *
 * @author xzwnp
 * 2022/4/3
 * 13:46
 * Steps：
 */
@Component
public class Task1 {
    /**
     * 每天凌晨五点执行一次
     */
    @Scheduled(cron = "0 0 5 * * ? ")
    public void task1(){
        System.out.println("五点了");
    }

    /**
     * 每五秒一次
     */
//    @Scheduled(cron = "0/5 * * * * ? ")
    public void task2(){
        System.out.println(new Date()+",hello");
    }
}
