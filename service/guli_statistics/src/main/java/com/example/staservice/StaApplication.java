package com.example.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

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
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
