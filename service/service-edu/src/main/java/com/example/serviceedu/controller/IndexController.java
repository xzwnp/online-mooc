package com.example.serviceedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.R;
import com.example.serviceedu.entity.EduCourse;
import com.example.serviceedu.entity.EduTeacher;
import com.example.serviceedu.service.EduCourseService;
import com.example.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.example.serviceedu.controller
 *
 * @author xzwnp
 * 2022/3/23
 * 16:51
 * Steps：
 */
@RestController
@RequestMapping("eduservice/index")
@Api(tags = "获取首页数据")
public class IndexController {
	@Autowired
	EduTeacherService teacherService;
	@Autowired
	EduCourseService courseService;

	@GetMapping
	@ApiOperation("获取前8热门课程和前4位最热名师")
	public R index() {
		List<EduTeacher> teacherList = teacherService.listCache();
		List<EduCourse> courseList = courseService.listCache();
		return R.ok().data("courseList", courseList).data("teacherList", teacherList);
	}

}
