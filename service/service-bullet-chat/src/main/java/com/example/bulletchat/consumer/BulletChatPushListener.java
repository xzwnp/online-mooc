package com.example.bulletchat.consumer;

import com.example.bulletchat.config.MyRabbitBeanConfig;
import com.example.bulletchat.entity.BulletChat;
import com.example.bulletchat.service.BulletChatService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * com.example.bulletchat.consumer
 *
 * @author xiaozhiwei
 * 2023/3/13
 * 17:48
 */
@RabbitListener(queues = "#{bulletPublishQueue.name}")
@Component
@Slf4j
public class BulletChatPushListener {
    public String queueName = MyRabbitBeanConfig.BULLET_PUSH_QUEUE_PREFIX;

    @Autowired
    BulletChatService bulletChatService;

    @RabbitHandler(isDefault = true)
    public void saveBulletChat(BulletChat bulletChat, Channel channel, Message message) {

        log.info("收到弹幕推送消息,弹幕id" + bulletChat.getId());
        bulletChatService.pushBullet(bulletChat);
        //手动应答
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
