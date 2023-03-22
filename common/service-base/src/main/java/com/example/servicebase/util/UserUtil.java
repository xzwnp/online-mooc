package com.example.servicebase.util;

import com.example.commonutils.JwtEntity;
import io.jsonwebtoken.lang.Assert;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;

/**
 * com.example.servicebase.util
 *
 * @author xiaozhiwei
 * 2023/3/22
 * 8:40
 */
public class UserUtil {
    public static String getUserId() {
        JwtEntity user = getUser();
        Assert.notNull(user, "无法获取用户信息!用户未登录!");
        return user.getUserId();
    }

    public static JwtEntity getUser() {
        return (JwtEntity) SecurityUtils.getSubject().getPrincipal();
    }
}
