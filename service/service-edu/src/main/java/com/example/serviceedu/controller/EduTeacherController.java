package com.example.serviceedu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
import com.example.servicebase.exception.GuliException;
import com.example.serviceedu.entity.EduTeacher;
import com.example.serviceedu.entity.vo.TeacherQuery;
import com.example.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * 
 */
@Api(tags = "教师信息控制器")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
	@Autowired
	EduTeacherService eduTeacherService;

	@ApiOperation("查找全部")
	@GetMapping("findAll")
	public R findAllTeacher() {
		List<EduTeacher> list = eduTeacherService.list(null);
		return R.ok().data("items", list);
	}

	@ApiOperation("分页查询")
	@GetMapping("page/{page}/limit/{limit}")
	public R pageList(
		@ApiParam(value = "当前页码", required = true) @PathVariable int page,
		@ApiParam(value = "每页记录数", required = true) @PathVariable int limit) {
		Page<EduTeacher> pageParam = new Page<>(page, limit);
		eduTeacherService.page(pageParam, null);
		//查询结果
		List<EduTeacher> records = pageParam.getRecords();
		//库中有效记录数
		long total = pageParam.getTotal();
		return R.ok().data("total", total).data("rows", records);
	}

	@ApiOperation("删除")
	@DeleteMapping("{id}")
	public R delete(@PathVariable String id) {
		boolean flag = eduTeacherService.removeById(id);
		return flag ? R.ok() : R.error();
	}

	@ApiOperation("条件分页查询")
	@PostMapping("pageTeacherCondition/{current}/{limit}")
	public R pageTeacherCondition(
		@ApiParam(value = "当前页码", required = true) @PathVariable int current,
		@ApiParam(value = "每页记录数", required = true) @PathVariable int limit,
		@ApiParam(value = "条件") @RequestBody TeacherQuery teacherQuery
	) {
		Page<EduTeacher> page = new Page<>(current, limit);
		eduTeacherService.teacherPage(page, teacherQuery);
		List<EduTeacher> records = page.getRecords();
		long total = page.getTotal();
		return R.ok().data("total", total).data("rows", records);

	}

	/**
	 * 前端上传头像后点击保存,若保存成功会返回图片的url,然后前端提交新增讲师请求,携带该url一起发送
	 *
	 * @param teacher
	 * @return
	 */
	@ApiOperation(value = "新增讲师")
	@PostMapping("addTeacher")
	public R save(
		@ApiParam(name = "teacher", value = "讲师对象", required = true)
		@RequestBody EduTeacher teacher) {

		eduTeacherService.save(teacher);
		return R.ok();
	}

	@ApiOperation(value = "根据讲师的id查询讲师")
	@GetMapping("getTeacher/{id}")
	public R findById(@PathVariable String id) {
		EduTeacher teacher = eduTeacherService.getById(id);
		return R.ok().data("item", teacher);
	}

	@ApiOperation(value = "根据讲师的id修改讲师")
	@PutMapping("updateTeacher")
	public R updateById(@RequestBody EduTeacher teacher) {
		boolean flag = eduTeacherService.updateById(teacher);
		return flag ? R.ok() : R.error();
	}

	@ApiOperation("生成异常")
	@GetMapping("createError")
	public R createError() throws GuliException {
		System.out.println("开始打印错误");
		try {
			int a = 10 / 0;
		} catch (Exception e) {
			throw new GuliException(20001, "不能除0异常");
		}
		return R.ok();
	}

}

