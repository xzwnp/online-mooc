package com.example.serviceedu.controller;


import com.example.commonutils.R;
import com.example.serviceedu.entity.EduChapter;
import com.example.serviceedu.entity.chapter.ChapterVo;
import com.example.serviceedu.service.EduChapterService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/eduservice/chapter")
@Api(tags = "章节控制器")
public class EduChapterController {
	@Autowired
	private EduChapterService chapterService;

	@ApiOperation(value = "获取某个课程的所有章节和所有小节")
	@GetMapping("getChapterVideo/{courseId}")
	public R getChapterVideo(@PathVariable String courseId) {
		List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
		return R.ok().data("allChapterVideo", list);
	}

	//添加章节
	@PostMapping("addChapter")
	@ApiOperation("新增章节")
	@RequiresRoles("course_admin")
	public R addChapter(@RequestBody EduChapter eduChapter) {
		chapterService.save(eduChapter);
		return R.ok();
	}

	//根据章节id查询
	@GetMapping("getChapterInfo/{chapterId}")
	@ApiOperation("获取章节信息")
	public R getChapterInfo(@PathVariable String chapterId) {
		EduChapter eduChapter = chapterService.getById(chapterId);
		return R.ok().data("chapter", eduChapter);
	}

	//修改章节
	@PostMapping("updateChapter")
	@ApiOperation("修改章节信息")
	@RequiresRoles("course_admin")
	public R updateChapter(@RequestBody EduChapter eduChapter) {
		chapterService.updateById(eduChapter);
		return R.ok();
	}

	//删除的方法
	@DeleteMapping("{chapterId}")
	@ApiOperation("删除整个章节")
	@RequiresRoles("course_admin")
	public R deleteChapter(@PathVariable String chapterId) {
		boolean flag = chapterService.deleteChapter(chapterId);
		if (flag) {
			return R.ok();
		} else {
			return R.error();
		}

	}
}

