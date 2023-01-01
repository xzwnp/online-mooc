package com.example.oss.controller;

import com.example.commonutils.R;
import com.example.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * com.example.oss.controller
 *
 * @author xzwnp
 * 2022/1/28
 * 19:07
 * Steps：
 */
@RestController
@RequestMapping("eduoss")
@Api("文件上传控制器")
public class OssController {
	@Autowired
	OssService ossService;
	//上传头像的方法
	@Value("${aliyun.oss.file.endpoint}")
	private String useLocalCache;

	@RequestMapping("/get")
	public String get() {
		return "useLocalCache:" + useLocalCache;
	}

	@PostMapping("fileoss")
	public R uploadOssFile(MultipartFile file) {
		String url = ossService.uploadFile(file);
		return R.ok().data("url", url);
	}
}
