package com.example.serviceedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * PACKAGE_NAME
 *
 * @author xzwnp
 * 2022/1/23
 * 22:24
 * Steps：
 */
@SpringBootApplication
//为了扫描到servicebase下的配置类,需要修改默认的包扫描
@ComponentScan(basePackages = {"com.example"})
@EnableDiscoveryClient //nacos注册
@EnableFeignClients //openFeign服务调用
@MapperScan("com.example.serviceedu.mapper")
public class EduApplication {
	public static void main(String[] args) {
		SpringApplication.run(EduApplication.class, args);
	}

}
