package com.example.serviceorder.client;

import com.example.commonutils.vo.CourseOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * com.example.serviceorder.client
 *
 * @author xzwnp
 * 2022/4/1
 * 23:17
 * Steps：
 */
@Component
@FeignClient("service-edu")
public interface EduClient {
    //根据课程id查询课程信息
    @GetMapping("/eduservice/course/courseOrderInfo/{courseId}")
    CourseOrderVo getInfo(@PathVariable("courseId") String id);
}
