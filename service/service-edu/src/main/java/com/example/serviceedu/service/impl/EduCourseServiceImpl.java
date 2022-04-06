package com.example.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.vo.CourseOrderVo;
import com.example.servicebase.exception.GuliException;
import com.example.serviceedu.entity.EduCourse;
import com.example.serviceedu.entity.EduCourseDescription;
import com.example.serviceedu.entity.EduTeacher;
import com.example.serviceedu.entity.dto.CourseInfoDto;
import com.example.serviceedu.entity.vo.CourseInfoForm;
import com.example.serviceedu.entity.vo.CourseInfoVo;
import com.example.serviceedu.entity.vo.CoursePublishVo;
import com.example.serviceedu.entity.vo.CourseQueryVo;
import com.example.serviceedu.mapper.EduCourseMapper;
import com.example.serviceedu.service.EduChapterService;
import com.example.serviceedu.service.EduCourseDescriptionService;
import com.example.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serviceedu.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-01-31
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    //课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    //注入小节和章节service
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService chapterService;

    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1 向课程表添加课程基本信息
        //CourseInfoVo对象转换eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            //添加失败
            throw new GuliException(20001, "添加课程信息失败");
        }

        //获取添加之后课程id
        String cid = eduCourse.getId();

        //2 向课程简介表添加课程简介
        //edu_course_description
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = null;
        try {
            publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publishCourseInfo;
    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0) { //失败返回
            throw new GuliException(20001, "删除失败");
        }
    }

    @Override
    @Cacheable(value = "index", key = "'courseList'")
    public List<EduCourse> listCache() {
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        return baseMapper.selectList(courseWrapper);
    }

    @Override
    public Page<EduCourse> conditionalPageList(Page<EduCourse> page, CourseQueryVo courseQueryVo) {
        if (courseQueryVo == null) {
            return (Page<EduCourse>) baseMapper.selectPage(page, null);
        }
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQueryVo.getTitle())) {
            wrapper.like("title", courseQueryVo.getTitle());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getTeacherId())) {
            wrapper.eq("teacher_id", courseQueryVo.getTeacherId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())) {
            wrapper.eq("subject_id", courseQueryVo.getSubjectId());
        } else if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseQueryVo.getSubjectParentId());
        }
        String columnName;
        switch (courseQueryVo.getSort()==null?"":courseQueryVo.getSort()) {
            case "1":
                columnName = "gmt_create";
                break;
            case "2":
                columnName = "buy_count";
                break;
            case "3":
                columnName = "price";
                break;
            default:
                //暂定默认为最新
                columnName = "gmt_create";
                break;
        }
        if (courseQueryVo.isDesc()) {
            wrapper.orderByDesc(columnName);
        } else {
            wrapper.orderByAsc(columnName);
        }
        baseMapper.selectPage(page, wrapper);
        return page;
    }

    @Override
    public CourseInfoDto getCourseAndTeacherInfoById(String id) {
        return baseMapper.getCourseAndTeacherInfoById(id);
    }



}
