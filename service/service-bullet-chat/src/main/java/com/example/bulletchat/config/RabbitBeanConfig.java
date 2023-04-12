package com.example.bulletchat.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * @author xiaozhiwei
 * 2022/11/18
 * 17:17
 */
@Configuration
public class RabbitBeanConfig {
    public static final String BULLET_SAVE_EXCHANGE = "bulletSaveExchange";
    public static final String BULLET_SAVE_QUEUE = "bullet.save.queue";
    public static final String BULLET_SAVE_BINDING = "bulletSaveBinding";
    public static final String BULLET_PUSH_EXCHANGE = "bulletPushExchange";
    public static final String BULLET_PUSH_QUEUE_PREFIX = "bullet.push.queue.";
    @Value("${server.port}")
    private String port;


    @Bean
    public Exchange bulletSaveExchange() {
        return ExchangeBuilder.directExchange(BULLET_SAVE_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Exchange bulletPubilishExchange() {
        return ExchangeBuilder.fanoutExchange(BULLET_PUSH_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    public Queue bulletSaveQueue() {
        return new Queue(BULLET_SAVE_QUEUE, true, false, false);
    }


    @Bean
    public Binding bulletSaveBinding() {
        return BindingBuilder
                .bind(bulletSaveQueue())
                .to(bulletSaveExchange())
                .with(BULLET_SAVE_BINDING)
                .noargs();
    }

    @Bean
    public Queue bulletPublishQueue() {
        //由于绑定的是fan-out交换机,需要保证每个服务的队列都不一样,使用临时队列即可
        String queueName = BULLET_PUSH_QUEUE_PREFIX + UUID.randomUUID().toString().replace("-", "").substring(16, 32);

        return new Queue(queueName, true, false, true);
    }


    @Bean
    public Binding bulletPublishBinding() {
        return BindingBuilder
                .bind(bulletPublishQueue())
                .to(bulletPubilishExchange())
                .with(bulletPublishQueue().getName())
                .noargs();
    }


}
