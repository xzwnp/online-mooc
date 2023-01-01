package com.example.crmservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * com.example
 *
 * @author xzwnp
 * 2022/3/22
 * 11:01
 * Steps：
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
//@MapperScan("com.example.crmservice.mapper")
@EnableDiscoveryClient //nacos注册
@EnableFeignClients
@Controller
public class CrmServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CrmServiceApplication.class, args);
	}

	/**
	 * swagger
	 */
	@RequestMapping("/educms/v3/api-docs")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("/v3/api-docs");
		return modelAndView;
	}

}
