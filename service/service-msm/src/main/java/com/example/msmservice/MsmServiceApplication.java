package com.example.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * com.example
 *
 * @author xzwnp
 * 2022/3/25
 * 17:37
 * Steps：
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableConfigurationProperties
@EnableDiscoveryClient
@ComponentScan("com.example")
public class MsmServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmServiceApplication.class,args);
    }
}
