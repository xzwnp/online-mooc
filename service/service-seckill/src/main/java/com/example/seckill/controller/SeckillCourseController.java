package com.example.seckill.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.seckill.entity.SeckillCourse;
import com.example.seckill.service.SeckillCourseService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-15
 */
@RestController
@RequestMapping("service-sk/seckill/course")
public class SeckillCourseController {
	@Autowired
	SeckillCourseService seckillCourseService;

	@GetMapping("getSeckillInfo")
	public R<List<SeckillCourse>> getSeckillInfo() {
		return R.ok(seckillCourseService.getSeckillInfo());
	}

	@GetMapping("getKey")
	@RequiresAuthentication
	public R<String> getSecKillCourseKey(String courseId) {
		String key = seckillCourseService.getSeckillCourseKey(courseId);
		return R.ok(key);
	}

	@GetMapping("doSeckill")
	@RequiresAuthentication
	public R<?> doSeckill(Integer seckillId,String key){
		seckillCourseService.doSeckill(seckillId,key);
		return R.ok(null).setMsg("抢购成功,正在为您生成订单...");
	}
}

