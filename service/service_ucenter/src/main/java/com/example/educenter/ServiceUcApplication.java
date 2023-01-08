package com.example.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * com.example.educenter
 *
 * @author xzwnp
 * 2022/3/15
 * 21:24
 * Steps：
 */
@ComponentScan({"com.example"})
@SpringBootApplication//取消数据源自动配置
@MapperScan("com.example.educenter.mapper")
@EnableDiscoveryClient
@Controller
public class ServiceUcApplication {
	public static void main(String[] args) {

		SpringApplication.run(ServiceUcApplication.class, args);
	}

	/**
	 * swagger
	 */
	@RequestMapping("/educenter/v3/api-docs")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("/v3/api-docs");
		return modelAndView;
	}
}
