package com.example.serviceedu.controller;

import com.example.commonutils.R;
import com.example.serviceedu.entity.statistics.CourseSubjectStatistics;
import com.example.serviceedu.service.EduCourseService;
import com.example.serviceedu.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.Subject;
import java.util.List;

/**
 * com.example.serviceedu.controller
 *
 * @author xzwnp
 * 2022/1/28
 * 11:47
 * Steps：
 */
@RestController
@RequestMapping("/eduservice/statistic")
@Api(tags = "课程统计接口")
public class EduStatisticController {
	@Autowired
	EduCourseService courseService;

	//login
	@PostMapping("login")
	public R login() {
		return R.ok().data("token", "admin");
	}

	//info
	@GetMapping("subjectCourse")
	@ApiOperation("分类课程数统计")
	@RequiresRoles("leader")
	public R subjectCourseStatistic() {
		List<CourseSubjectStatistics> subjectStatistic = courseService.getSubjectStatistic();
		return R.ok().data("statistics", subjectStatistic);
	}
}
