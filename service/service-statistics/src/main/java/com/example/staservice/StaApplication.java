package com.example.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * com.example.staservice
 *
 * @author xzwnp
 * 2022/4/2
 * 20:23
 * Steps：
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.example")
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.example.staservice.mapper")
@EnableScheduling //开启定时任务
@Controller
public class StaApplication {
	public static void main(String[] args) {

		SpringApplication.run(StaApplication.class, args);
	}


	/**
	 * swagger
	 */
	@RequestMapping("/staservice/v3/api-docs")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("/v3/api-docs");
		return modelAndView;
	}
}
