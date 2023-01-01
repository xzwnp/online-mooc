package com.example.serviceedu.controller;


import com.example.commonutils.R;
import com.example.commonutils.ResultCode;
import com.example.serviceedu.client.VodClient;
import com.example.serviceedu.entity.EduVideo;
import com.example.serviceedu.entity.chapter.VideoVo;
import com.example.serviceedu.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Stack;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *

 * 
 */
@RestController
@RequestMapping("/eduservice/video")
@Api(tags = "课程小节信息")
public class EduVideoController {
	@Autowired
	EduVideoService videoService;

	@Autowired
	VodClient vodClient;

	@DeleteMapping("{videoId}")
	@ApiOperation("删除小节")
	public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
						 @PathVariable("videoId") String sectionId) {
		boolean flag = true;
		//说明:上面的命名有误,video应该是小节,下面的命名无误
		//每个小节有一个视频,还有其他一些信息(如标题),删除小节时输入小节编号,希望先删除在云存储的视频,再删除在数据库的数据
		//二次说明:以下代码应当写在service层
		EduVideo video = videoService.getById(sectionId);
		if (ObjectUtils.isEmpty(video)) {
			return R.error().message("找不到该小节!");
		}
		String videoId = video.getVideoSourceId();
		//判断小节里是否有视频id
		if (StringUtils.hasLength(videoId)) {
			R r = vodClient.removeVideo(videoId);
			if (!r.getCode().equals(ResultCode.SUCCESS)) {
				//如果失败了,触发以下条件
				return r;
			}
		}
		flag = videoService.removeById(sectionId);
		return flag ? R.ok().message("视频删除成功") : R.error().message("视频删除失败");
	}

	@PostMapping("addOrUpdateVideo")
	@ApiOperation("添加或删除小节")
	public R addViedo(@RequestBody VideoVo vo) {
		EduVideo eduVideo = new EduVideo();
		BeanUtils.copyProperties(vo, eduVideo);
		eduVideo.setIsFree(vo.isFree() ? 0 : 1);
		boolean flag = videoService.saveOrUpdate(eduVideo);
		return flag ? R.ok().message("小节编辑成功") : R.error().message("小节编辑失败");
	}

}

