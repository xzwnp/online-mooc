package com.example.bulletchat.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * com.example.bulletchat.config
 *
 * @author xiaozhiwei
 * 2023/4/2
 * 18:15
 */
@Component
public class DynamicQueueNameUtil implements InitializingBean {
    public static String queueName;

    @Override
    public void afterPropertiesSet() throws Exception {
        queueName = UUID.randomUUID().toString().replace("-","").substring(16,32);
    }
}
