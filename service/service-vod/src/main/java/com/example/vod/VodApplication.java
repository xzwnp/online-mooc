package com.example.vod;

import com.example.vod.utils.AliyunVodSDKUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;

/**
 * com.example.vod
 *
 * @author xzwnp
 * 2022/3/11
 * 15:18
 * 关于为什么要修改默认的ComponentScan:
 * 导入了service-base包,希望能扫描到com.example.servicebase下的swaggerConfig自动配置类
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.example"})
@EnableDiscoveryClient
@Controller
public class VodApplication {
	public static void main(String[] args) {
		SpringApplication.run(VodApplication.class, args);
	}

	/**
	 * swagger
	 */
	@RequestMapping("/eduvod/v3/api-docs")
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView("/v3/api-docs");
		return modelAndView;
	}
}
