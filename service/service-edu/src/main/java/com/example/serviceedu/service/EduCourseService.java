package com.example.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.vo.CourseOrderVo;
import com.example.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.serviceedu.entity.EduTeacher;
import com.example.serviceedu.entity.dto.CourseInfoDto;
import com.example.serviceedu.entity.vo.CourseInfoForm;
import com.example.serviceedu.entity.vo.CourseInfoVo;
import com.example.serviceedu.entity.vo.CoursePublishVo;
import com.example.serviceedu.entity.vo.CourseQueryVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-01-31
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息的方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);

    //删除课程
    void removeCourse(String courseId);

    List<EduCourse> listCache() ;

    Page<EduCourse> conditionalPageList(Page<EduCourse> page, CourseQueryVo courseQueryVo);

    CourseInfoDto getCourseAndTeacherInfoById(String id);

}
