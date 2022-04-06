package com.example.serviceedu.mapper;

import com.example.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.serviceedu.entity.dto.CourseInfoDto;
import com.example.serviceedu.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2022-01-31
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String id);

    CourseInfoDto getCourseAndTeacherInfoById(String id);
}
