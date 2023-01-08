package com.example.serviceedu.mapper;

import com.example.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.serviceedu.entity.dto.CourseInfoDto;
import com.example.serviceedu.entity.statistics.CourseSubjectStatistics;
import com.example.serviceedu.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *

 * 
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String id);

    CourseInfoDto getCourseAndTeacherInfoById(String id);

    List<CourseSubjectStatistics> getSubjectStatistic();
}
