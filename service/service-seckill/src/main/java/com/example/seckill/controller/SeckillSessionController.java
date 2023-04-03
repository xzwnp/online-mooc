package com.example.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.seckill.entity.SeckillCourse;
import com.example.seckill.entity.SeckillSession;
import com.example.seckill.service.SeckillSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * com.example.seckill.controller
 *
 * @author xiaozhiwei
 * 2023/4/1
 * 17:42
 */
@RestController
@RequestMapping("seckill/session")
@Api(tags = "秒杀场次管理")
public class SeckillSessionController {
    @Autowired
    SeckillSessionService seckillSessionService;

    /**
     * 暂时只支持获取已经结束的秒杀场次信息
     */
    @ApiOperation("获取秒杀场次信息")
    @GetMapping("")
    public R<?> getCurrentSeckillSessions() {
        List<SeckillSession> seckillSessions = seckillSessionService.getCurrentSeckillSessions();
        return R.ok(seckillSessions);
    }


    @ApiOperation("添加秒杀场次")
    @PostMapping("")
    @RequiresRoles("seckill_admin")
    public R<?> addSeckillSession(@RequestBody SeckillSession seckillSession) {
        seckillSessionService.save(seckillSession);
        return R.ok(null).setMsg("添加成功!");
    }

    @ApiOperation("新增秒杀场次")
    @PutMapping("")
    @RequiresRoles("seckill_admin")
    public R<?> updateSeckillService(@RequestBody SeckillSession seckillSession) {
        seckillSessionService.updateById(seckillSession);
        return R.ok(null).setMsg("更新成功!");
    }

    @ApiOperation("删除秒杀场次")
    @DeleteMapping("")
    @RequiresRoles("seckill_admin")
    public R<?> removeSeckillService(Integer seckillSessionId) {
        seckillSessionService.removeById(seckillSessionId);
        return R.ok(null).setMsg("删除成功!");
    }

}
