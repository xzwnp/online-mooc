package com.example.seckill.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.seckill.entity.SeckillCourse;
import com.example.seckill.entity.SeckillSession;
import com.example.seckill.service.SeckillCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-15
 */
@RestController
@RequestMapping("seckill/course")
@Api(tags = "课程秒杀控制器")
public class SeckillCourseController {
    @Autowired
    SeckillCourseService seckillCourseService;

    @GetMapping("getSeckillInfo")
    @ApiOperation("获取某一场次的秒杀信息")
    public R<List<SeckillCourse>> getSeckillInfo(Integer seckillSessionId) {
        return R.ok(seckillCourseService.getSeckillInfo(seckillSessionId));
    }

    @ApiOperation("获取某一秒杀课程的秒杀key")
    @GetMapping("getKey")
    @RequiresAuthentication
    public R<String> getSecKillCourseKey(String sessionId, String seckillId) {
        String key = seckillCourseService.getSeckillCourseKey(sessionId, seckillId);
        return R.ok(key);
    }

    @ApiOperation("进行秒杀")
    @PostMapping("doSeckill")
    @RequiresAuthentication
    public R<?> doSeckill(Integer sessionId, Integer seckillId, String key) {
        seckillCourseService.doSeckill(sessionId, seckillId, key);
        return R.ok(null).setMsg("抢购成功,正在为您生成订单...");
    }

    @ApiOperation("添加秒杀课程")
    @PostMapping("")
    @RequiresRoles("seckill_admin")
    public R<?> addSeckillSession(@RequestBody SeckillCourse seckillCourse) {
        seckillCourseService.save(seckillCourse);
        return R.ok(null).setMsg("添加成功!");
    }

    @ApiOperation("更新秒杀课程")

    @PutMapping("")
    @RequiresRoles("seckill_admin")
    public R<?> updateSeckillService(@RequestBody SeckillCourse seckillCourse) {
        seckillCourseService.updateById(seckillCourse);
        return R.ok(null).setMsg("更新成功!");
    }

    @ApiOperation("删除秒杀课程")
    @DeleteMapping("")
    @RequiresRoles("seckill_admin")
    public R<?> removeSeckillService(Integer seckillCourseId) {
        seckillCourseService.removeById(seckillCourseId);
        return R.ok(null).setMsg("删除成功!");
    }


}

