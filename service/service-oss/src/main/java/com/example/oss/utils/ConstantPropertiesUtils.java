package com.example.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * com.example.oss.utils
 *
 * @author xzwnp
 * 2022/1/28
 * 18:53
 * 常用属性类,从application.properties中读取阿里云oss配置信息
 * 实现InitializingBean方法,当组件的几个成员变量被赋值后,会执行afterPropertiesSet()
 */
@Component
//@ConfigurationProperties(prefix = "aliyun.oss.file")
public class ConstantPropertiesUtils implements InitializingBean {
    @Value("${aliyun.oss.file.endpoint}")
	private String endpoint;

	@Value("${aliyun.oss.file.keyid}")
	private String keyid;

	@Value("${aliyun.oss.file.keysecret}")
	private String keysecret;
	@Value("${aliyun.oss.file.bucketname}")
	private String bucketname;
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;
    }

}
