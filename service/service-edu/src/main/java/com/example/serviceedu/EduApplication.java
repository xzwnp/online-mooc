package com.example.serviceedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

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
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
