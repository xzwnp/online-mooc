package com.example.serviceorder.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * com.example.serviceorder.util
 *
 * @author xzwnp
 * 2022/4/2
 * 13:45
 * Steps：
 */
public class OrderNoUtil {

    public static String getOrderNo() {
        //生成方式:年月日时分秒+3位随机数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(new Date());
        StringBuilder sb = new StringBuilder(date);
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
