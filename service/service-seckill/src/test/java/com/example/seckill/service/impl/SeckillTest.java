package com.example.seckill.service.impl;

import com.example.seckill.service.SeckillCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * com.example.seckill.service.impl
 *
 * @author xiaozhiwei
 * 2023/4/2
 * 11:41
 */
@SpringBootTest
public class SeckillTest {
    @Autowired
    SeckillCourseService seckillCourseService;

    @Test
    void shelf() {
        seckillCourseService.shelf(LocalDateTime.now().minusDays(1));
    }
}
