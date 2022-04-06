package com.example.serviceedu.controller;


import com.example.commonutils.R;
import com.example.serviceedu.entity.subject.PrimaryClassification;
import com.example.serviceedu.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-01-30
 */
@Api("课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    EduSubjectService eduSubjectService;

    /**
     * 存入数据库
     * @param file
     * @return
     */
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.addSubject(file);
        return R.ok();
    }

    @ApiOperation("获取课程信息,并以树状图显示")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<PrimaryClassification> allSubject = eduSubjectService.getAllSubject();
        return R.ok().data("list",allSubject);
    }

}

