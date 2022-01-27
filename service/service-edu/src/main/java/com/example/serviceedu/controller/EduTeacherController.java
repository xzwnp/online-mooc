package com.example.serviceedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
import com.example.serviceedu.entity.EduTeacher;
import com.example.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-01-23
 */
@RestController
@RequestMapping("/serviceedu/edu-teacher")
@Api("edu-teacher")
public class EduTeacherController {
    @Autowired
    EduTeacherService eduTeacherService;

    @ApiOperation("查找全部")
    @GetMapping("findAll")
    public R findAllTeacher() {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation("分页查询")
    @GetMapping("page/{page}/limit/{limit}")
    public R pageList(
            @ApiParam(value = "当前页码", required = true) @PathVariable int page,
            @ApiParam(value = "每页记录数", required = true) @PathVariable int limit) {
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        eduTeacherService.page(pageParam, null);
        //查询结果
        List<EduTeacher> records = pageParam.getRecords();
        //库中有效记录数
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("删除")
    @DeleteMapping("delete/{id}")
    public R delete(@PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        return flag ? R.ok() : R.error();
    }

}

