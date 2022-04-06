package com.example.serviceorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

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
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class);
    }
}
