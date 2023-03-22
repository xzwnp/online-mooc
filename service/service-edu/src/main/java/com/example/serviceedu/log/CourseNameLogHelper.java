package com.example.serviceedu.log;

import com.example.serviceedu.entity.vo.CourseInfoVo;
import com.example.serviceedu.service.EduCourseService;
import icu.ynu.log.annotation.LogRecordFunction;
import icu.ynu.log.annotation.LogRecordFunctionBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * com.example.serviceedu.log
 *
 * @author xiaozhiwei
 * 2023/3/22
 * 9:13
 */
@LogRecordFunctionBean
public class CourseNameLogHelper implements InitializingBean {
    @Autowired
    EduCourseService ecs;
    static EduCourseService eduCourseService;

    @LogRecordFunction("getCourseName")
    public static String getCourseName(String courseId) {
        CourseInfoVo courseInfo = eduCourseService.getCourseInfo(courseId);
        return courseInfo.getTitle();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        eduCourseService = ecs;
    }
}
