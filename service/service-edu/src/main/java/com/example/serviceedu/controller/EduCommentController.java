package com.example.serviceedu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.R;
import com.example.serviceedu.entity.EduComment;
import com.example.serviceedu.entity.form.CommentForm;
import com.example.serviceedu.entity.vo.CommentVo;
import com.example.serviceedu.service.EduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * com.example.serviceedu.controller
 *
 * @author xzwnp
 * 2022/12/31
 * 9:42
 */
@RestController
@RequestMapping("eduservice/comment")
@Api(tags = "评论管理")
@Slf4j
public class EduCommentController {
	@Autowired
	EduCommentService commentService;


	@PostMapping("create")
	@ApiOperation("新建评论")
	public R create(@RequestBody CommentForm form) {
		commentService.createComment(form);
		return R.ok();
	}

	@GetMapping("{videoId}/current/{current}/size/{size}")
	@ApiOperation("根据视频id查找,并以分页树形结构返回")
	public R getCommentsByVideoId(@PathVariable String videoId, @PathVariable int current, @PathVariable int size) {
		Page<CommentVo> page = commentService.treeCommentPage(videoId, current, size);
		return R.ok().data("total", page.getTotal()).data("comments", page.getRecords());
	}


	@DeleteMapping("{commentId}")
	@ApiOperation("删除评论")
	public R removeComment(@PathVariable Integer commentId) {

		return commentService.removeById(commentId) ? R.ok() : R.error().message("删除失败!");
	}
}
