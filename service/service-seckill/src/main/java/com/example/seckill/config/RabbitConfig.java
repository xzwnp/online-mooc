package com.example.seckill.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * com.example.bulletchat.config
 *
 * @author xiaozhiwei
 * 2023/3/13
 * 16:36
 */
@Configuration
@Slf4j
public class RabbitConfig {
    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct //构造函数执行完毕后,调用该方法
    public void initRabbitTemplate() {
        /**
         *
         * @param correlationData 消息的id
         * @param ack 是否收到
         * @param cause 失败原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.info("消息id:{}未收到,原因:{}", correlationData, cause);
            } else {
                log.info("消息id:{}已送出", correlationData);
            }
        });

        /**
         * 如果有消息没有投递给指定的队列,会触发这个回调,发送成功不触发
         * @param message 失败的消息
         * @param replyCode 状态码
         * @param replyText 失败原因
         * @param exchange 发给哪个交换机
         * @param routingKey 发给哪个路由键
         */
        rabbitTemplate.setReturnsCallback((returnedMessage) -> {
            log.info("消息投递失败。错误信息::message:{}\nreplyCode:{},replyText:{}\n,exchange:{},routingKey:{}",
                    returnedMessage.getMessage(), returnedMessage.getReplyCode(), returnedMessage.getReplyText()
                    , returnedMessage.getExchange(), returnedMessage.getRoutingKey());
        });
    }


}
