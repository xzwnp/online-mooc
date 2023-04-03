package com.example.seckill.util;

import com.example.commonutils.SnowFlakeUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;

/**
 * com.example.seckill.util
 *
 * @author xiaozhiwei
 * 2023/4/2
 * 15:38
 */
public class CorrelationDataUtil {

    //todo 从配置文件读取
    static SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(1, 9);

    public static CorrelationData generate() {
        long id = snowFlakeUtil.getNextId();
        return new CorrelationData(String.valueOf(id));
    }
}
