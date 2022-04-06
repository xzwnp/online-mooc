package com.example.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * com.example.vod.utils
 *
 * @author xzwnp
 * 2022/3/10
 * 19:02
 * 警告:@ConfigurationProperties只会调用 非静态的set方法
 * value 注解同理
 * 声明了两组变量,一组静态,一组非静态,非静态是为了实现配置绑定,静态是为了能通过类名直接调用,而不用创建对象
 * ignoreUnknownFields=false ,当无法赋值时会报错
 */
@Component
@ConfigurationProperties(prefix = "aliyun.vod.file", ignoreUnknownFields = false)
public class AliyunVodSDKUtils implements InitializingBean {
    private String keyId;
    private String keySecret;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeySecret() {
        return keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }

    /**
     * 使用音视频播放需要先初始化client
     */
    public static DefaultAcsClient getClient() throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        return new DefaultAcsClient(profile);
    }
}
