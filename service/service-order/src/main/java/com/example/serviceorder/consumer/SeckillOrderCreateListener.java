package com.example.serviceorder.consumer;

import com.example.serviceorder.config.RabbitBeanConfig;
import com.example.commonutils.vo.SeckillCourseOrder;
import com.example.serviceorder.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xiaozhiwei
 * 2023/3/13
 * 17:48
 */
@RabbitListener(queues = RabbitBeanConfig.SECKILL_ORDER_SAVE_QUEUE)
@Component
@Slf4j
public class SeckillOrderCreateListener {
    @Autowired
    OrderService orderService;

    @RabbitHandler(isDefault = true)
    public void createOrder(SeckillCourseOrder orderInfo, Channel channel, Message message) {

        log.info("消费者-正在创建订单" + message.getMessageProperties().getCorrelationId());
        orderService.saveSeckillOrder(orderInfo);
        //手动应答
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
