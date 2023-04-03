package com.example.servicebase.shiro.utils;

import com.example.commonutils.JwtEntity;
import com.example.commonutils.ResultCode;
import com.example.servicebase.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SubjectContext;

import javax.security.auth.Subject;

/**
 * com.example.servicebase.shiro.utils
 *
 * @author xzwnp
 * 2023/3/15
 * 22:21
 */
@Slf4j
public class ShiroUtil {
    public static String getUserId() {
        JwtEntity user = getUser();
        return user.getUserId();
    }

    public static JwtEntity getUser() {
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            throw new GlobalException(ResultCode.ERROR, "用户未登录!无法获取用户信息");
        }
        try {
            JwtEntity user = (JwtEntity) (SecurityUtils.getSubject().getPrincipal());
            return user;
        } catch (Exception e) {
            throw new GlobalException(ResultCode.ERROR, "无法获取用户信息");
        }


    }
}
