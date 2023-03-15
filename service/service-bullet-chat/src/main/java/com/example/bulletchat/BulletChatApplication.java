package com.example.bulletchat;

import com.alibaba.cloud.nacos.NacosConfigAutoConfiguration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * com.example.bulletchat
 *
 * @author xiaozhiwei
 * 2023/3/12
 * 15:50
 */
@SpringBootApplication(exclude = {NacosConfigAutoConfiguration.class})
@ComponentScan(basePackages = "com.example")
@EnableRabbit
public class BulletChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(BulletChatApplication.class, args);
    }
}
