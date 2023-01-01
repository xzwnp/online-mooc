package com.example.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * com.example.oss
 *
 * @author xzwnp
 * 2022/1/28
 * 18:32
 * 说明:没有配置数据源,所以要排除数据源自动配置类
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.example"})
@EnableDiscoveryClient
@Controller
public class OssApplication {
	public static void main(String[] args) {

		SpringApplication.run(OssApplication.class, args);
	}

	/**
	 * swagger
	 */
	@RequestMapping("/eduoss/v3/api-docs")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("/v3/api-docs");
		return modelAndView;
	}
}
