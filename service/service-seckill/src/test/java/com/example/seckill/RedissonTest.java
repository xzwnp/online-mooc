package com.example.seckill;

import org.junit.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * com.example.seckill
 *
 * @author xiaozhiwei
 * 2023/3/15
 * 14:21
 */
@SpringBootTest
public class RedissonTest {
    @Autowired
    RedissonClient redissonClient;

    @Test
    public void name() {
        Runnable runnable = () -> {
            RLock lock = redissonClient.getLock("test::lock");

            try {
                boolean result = lock.tryLock(1, TimeUnit.MINUTES);
                System.out.println(Thread.currentThread().getName() + "加锁情况:" + result);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(runnable).start();
            countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
