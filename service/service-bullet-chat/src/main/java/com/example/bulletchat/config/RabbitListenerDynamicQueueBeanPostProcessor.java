package com.example.bulletchat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 想要利用fanOut交换机实现广播,我们需要让每个实例都有一个动态生成的队列名称
 * 问题来了,@RabbitListener注解的队列名称必须是常量,应该怎么实现动态?
 * 答案就是这个BeanPostProcessor
 */
//@Component
@Slf4j
public class RabbitListenerDynamicQueueBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware {
    private BeanFactory beanFactory;
    @Value("${server.port}")
    private String port;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        Collection<RabbitListener> listenerAnnotations = findListenerAnnotations(targetClass);
        if (!CollectionUtils.isEmpty(listenerAnnotations)) {
            for (RabbitListener rabbitListener : listenerAnnotations) {
                boolean isDynamic = false;
                String[] queues = rabbitListener.queues();
                if (queues.length > 0) {
                    for (int i = 0; i < queues.length; ++i) {
                        String queue = queues[i];
//                        我们需要处理RabbitListener注解的queues属性,把其中的#dynamic#替换为ip+端口号,从而实现动态队列名称
                        if (queue.startsWith("#dynamic#")) {
                            queues[i] = resolveDynamicQueueName(queues[i]);
                            isDynamic = true;
                        }
                    }
                }

                if (isDynamic) {
                    try {
                        InvocationHandler invocationHandler = Proxy.getInvocationHandler(rabbitListener);
                        Field memberValues = invocationHandler.getClass().getDeclaredField("valueCache");
                        memberValues.setAccessible(true);
                        ConcurrentHashMap memberValuesValue = (ConcurrentHashMap) memberValues.get(invocationHandler);
                        memberValuesValue.put("queues", queues);
                    } catch (Exception e) {
                        log.error("RabbitListenerDynamicQueueBeanPostProcessor can't process dynamic queue.", e);
                    }
                }
            }

        }
        return bean;
    }

    private String resolveDynamicQueueName(String queue) {
        //获取ip地址
        InetUtils inetUtils = this.beanFactory.getBean(InetUtils.class);
        //首先排除回环地址127
        InetUtils.HostInfo firstNonLoopbackHostInfo = inetUtils.findFirstNonLoopbackHostInfo();
        String ipAddress = firstNonLoopbackHostInfo.getIpAddress();
        //再加个随机数
        queue = queue + ipAddress + "." + port;
        log.info("RabbitListenerDynamicQueueBeanPostProcessor modify queue:{}", queue);
        return queue;
    }

    private Collection<RabbitListener> findListenerAnnotations(Class<?> clazz) {
        Set<RabbitListener> listeners = new HashSet<>();
        RabbitListener ann = AnnotationUtils.findAnnotation(clazz, RabbitListener.class);
        if (ann != null) {
            listeners.add(ann);
        }

        RabbitListeners anns = AnnotationUtils.findAnnotation(clazz, RabbitListeners.class);
        if (anns != null) {
            Collections.addAll(listeners, anns.value());
        }

        return listeners;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * order为2147483646是因为spring整合rabbitMq时，注册监听队列用的后置处理类RabbitListenerAnnotationBeanPostProcessor
     * 的order为2147483647，我们必须在注册之前将队列名称修改
     */
    @Override
    public int getOrder() {
        return 2147483646;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}

