package com.example.serviceorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * com.example.serviceorder
 *
 * @author xzwnp
 * 2022/4/1
 * 21:16
 * Steps：
 */
@SpringBootApplication
@ComponentScan({"com.example"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.example.serviceorder.mapper")
@Controller
public class OrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class);
	}

	/**
	 * swagger
	 */
	@RequestMapping("/orderservice/v3/api-docs")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("/v3/api-docs");
		return modelAndView;
	}
}
