package com.example.serviceedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
import com.example.commonutils.vo.CourseOrderVo;
import com.example.servicebase.exception.GlobalException;
import com.example.serviceedu.entity.EduCourse;
import com.example.serviceedu.entity.chapter.ChapterVo;
import com.example.serviceedu.entity.dto.CourseInfoDto;
import com.example.serviceedu.entity.vo.CourseInfoVo;
import com.example.serviceedu.entity.vo.CoursePublishVo;
import com.example.serviceedu.entity.vo.CourseQueryVo;
import com.example.serviceedu.service.EduChapterService;
import com.example.serviceedu.service.EduCourseService;
import icu.ynu.log.annotation.LogRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 */
@Api(tags = "课程控制器")
@RestController
@RequestMapping("/eduservice/course")
@Slf4j
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;

    //课程列表 基本实现
    @ApiOperation("分页查询课程列表")
    @PostMapping("current/{current}/size/{size}")
    public R getCourseList(@PathVariable Integer current, @PathVariable Integer size, @RequestBody(required = false) CourseQueryVo courseQueryVo) {
        Page<EduCourse> page = new Page<>(current, size);
        try {
            courseService.conditionalPageList(page, courseQueryVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok().data("list", page.getRecords()).data("total", page.getTotal());
    }

    @ApiOperation("新增课程")
    @PostMapping("addCourseInfo")
    @RequiresRoles("course_admin")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    @ApiOperation("根据课程id查询课程基本信息,不包括章节信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    @ApiOperation("修改课程信息")
    @RequiresRoles("course_admin")
    @LogRecord(bizId = "#courseInfoVo.id", bizType = "课程基本信息", content = "'修改了课程信息'")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @GetMapping("getPublishCourseInfo/{id}")
    @ApiOperation("发布课程时的课程预览,后台管理系统用")
    @RequiresRoles("course_admin")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    @ApiOperation("课程上架")
    @RequiresRoles("course_admin")
    @LogRecord(bizId = "#id", bizType = "课程基本信息", content = "'上架了课程,课程id是:{}'")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");//设置课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    @ApiOperation("删除课程")
    @RequiresRoles("course_admin")
    @LogRecord(bizId = "#courseId", bizType = "课程基本信息", content = "'删除了课程,课程id是:{}'")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

    @GetMapping("getFrontCourseInfo/{id}")
    @ApiOperation("获取课程信息(用户端)")
    public R getFrontCourseInfo(@PathVariable String id) {
        try {
            //查询课程信息,教师信息,课程简介,所属一级/二级学科
            CourseInfoDto courseAndTeacherInfo = courseService.getCourseAndTeacherInfoById(id);
            //查询该课程的所有章节
            List<ChapterVo> chapterList = chapterService.getChapterVideoByCourseId(id);
            return R.ok().data("course", courseAndTeacherInfo).data("chapterVoList", chapterList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(20001, "查询失败");
        }

    }

    @ApiOperation("获取生成订单所需的课程信息")
    @GetMapping("courseOrderInfo/{courseId}")
    public CourseOrderVo getCourseInfoDto(@PathVariable String courseId) {
        CourseInfoDto courseInfoDto = courseService.getCourseAndTeacherInfoById(courseId);
        CourseOrderVo courseOrderVo = new CourseOrderVo();
        BeanUtils.copyProperties(courseInfoDto, courseOrderVo);
        return courseOrderVo;
    }


}

