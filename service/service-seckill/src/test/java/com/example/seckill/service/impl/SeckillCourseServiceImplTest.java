package com.example.seckill.service.impl;

import com.example.seckill.entity.SeckillCourse;
import com.example.seckill.service.SeckillCourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * com.example.seckill.service.impl
 *
 * @author xiaozhiwei
 * 2023/3/15
 * 18:31
 */
@SpringBootTest
class SeckillCourseServiceImplTest {
    @Autowired
    SeckillCourseService service;

    @Test
    void shelf() {
        service.shelf();
    }
}