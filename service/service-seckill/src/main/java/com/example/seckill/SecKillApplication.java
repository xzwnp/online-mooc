package com.example.seckill;

import com.example.seckill.mapper.SeckillCourseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * com.example.seckill
 *
 * @author xiaozhiwei
 * 2023/3/15
 * 14:19
 */
@SpringBootApplication
@ComponentScan("com.example")
@MapperScan(basePackageClasses = SeckillCourseMapper.class)
@EnableScheduling
public class SecKillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecKillApplication.class, args);
    }
}
