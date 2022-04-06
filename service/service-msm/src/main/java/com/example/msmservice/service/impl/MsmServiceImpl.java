package com.example.msmservice.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.example.msmservice.service.MsmService;
import com.example.msmservice.util.MsmUtil;
import com.example.servicebase.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * com.example.msmservice.service.impl
 *
 * @author xzwnp
 * 2022/3/25
 * 17:58
 * Steps：
 */
@Service
@Slf4j
public class MsmServiceImpl implements MsmService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public String sendMessage(String phone) {
        String code = this.generateCode();
        Client client = null;
        try {
            client = MsmUtil.createClient();
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName("阿里云短信测试")
                    .setTemplateCode("SMS_154950909")
                    .setPhoneNumbers(phone)
                    .setTemplateParam("{\"code\":\"" + code + "\"}");
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
            if (!"OK".equals(sendSmsResponse.body.code)){
                log.error(sendSmsResponse.body.message);
                throw new GuliException(20001, "发送失败!");
            }
            //把验证码加入缓存,设置为5分钟内有效
            redisTemplate.opsForValue().set("VerificationCode::"+phone,code,5, TimeUnit.MINUTES);
            return code;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "短信服务初始化失败!");
        }
    }

    private String generateCode() {
        //生成6位验证码
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }
}
