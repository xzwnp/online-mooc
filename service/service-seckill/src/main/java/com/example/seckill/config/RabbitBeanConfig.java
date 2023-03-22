package com.example.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaozhiwei
 * 2022/11/18
 * 17:17
 */
@Configuration
public class RabbitBeanConfig {
	public static final String SECKILL_ORDER_SAVE_EXCHANGE = "seckillOrderSaveExchange";
	public static final String SECKILL_ORDER_SAVE_QUEUE = "seckill-order.save.queue";
	public static final String SECKILL_ORDER_SAVE_BINDING = "seckillOrderSaveBinding";


	@Bean
	public Exchange bulletSaveExchange() {
		return ExchangeBuilder.directExchange(SECKILL_ORDER_SAVE_EXCHANGE)
			.durable(true)
			.build();
	}


	@Bean
	public Queue bulletSaveQueue() {
		return new Queue(SECKILL_ORDER_SAVE_QUEUE, true, false, false);
	}


	@Bean
	public Binding bulletSaveBinding() {
		return BindingBuilder
			.bind(bulletSaveQueue())
			.to(bulletSaveExchange())
			.with(SECKILL_ORDER_SAVE_BINDING)
			.noargs();
	}


}
