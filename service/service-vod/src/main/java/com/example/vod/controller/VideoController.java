package com.example.vod.controller;

import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.example.commonutils.R;
import com.example.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * com.example.vod.controller
 *
 * @author xzwnp
 * 2022/3/10
 * 19:04
 * Steps：
 */
@RestController
@RequestMapping("eduvod/video")
@Api("视频上传获取控制器")
@Slf4j
public class VideoController {
	@Autowired
	VideoService videoService;

	@GetMapping("getVideo/{videoId}")
	@ApiOperation("输入视频号,获取视频的所有播放地址")
//	@RequiresAuthentication
	public R getVideo(@PathVariable String videoId) {
		try {
			GetPlayInfoResponse response = videoService.getVideoPlayInfo(videoId);
			List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
			//播放地址,一个视频可能有多个,如流畅,高清,原画
			for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
				System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
			}
			//Base信息
			System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
			//requestId:暂时不知道干什么的
			System.out.print("RequestId = " + response.getRequestId() + "\n");
			return R.ok().data("title", response.getVideoBase().getTitle()).data("urls", playInfoList);
		} catch (Exception e) {
			System.out.print("ErrorMessage = " + e.getLocalizedMessage());
			return R.error().message("无法获取视频播放地址!");
		}
	}

	@PostMapping("uploadAlyVideo")
	@ApiOperation("上传视频")
	@RequiresRoles("course_admin")
	public R uploadVideo(
		@ApiParam(name = "file", value = "文件", required = true)
		@RequestParam("file") MultipartFile file) {

		String videoId = videoService.uploadVideoPlay(file);
		return R.ok().message("视频上传成功").data("videoId", videoId);
	}

	@DeleteMapping("removeAlyVideo/{id}")
	@ApiOperation("删除视频")
	@RequiresRoles("course_admin")
	public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
						 @PathVariable("id") String videoId) {

		videoService.removeVideo(videoId);
		return R.ok().message("视频删除成功");
	}

	@GetMapping("getPlayAuth/{id}")
	@ApiOperation("获取视频播放凭证")
	@RequiresAuthentication
	public R getPlayAuth(@ApiParam(name = "videoId", value = "云端视频id", required = true)
						 @PathVariable("id") String videoId) {
		log.info(videoId);
		String playAuth = videoService.getVideoPlayAuth(videoId);
		if (StringUtils.hasLength(playAuth)) {
			return R.ok().data("playAuth", playAuth);
		} else {
			return R.error().message("获取视频信息失败!");
		}

	}

}
