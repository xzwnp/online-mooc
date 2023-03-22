package com.example.servicebase.shiro.utils;

import com.example.commonutils.JwtEntity;
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
		return user == null ? null : user.getUserId();
	}

	public static JwtEntity getUser() {
		if (SecurityUtils.getSubject().isAuthenticated()) {
			log.error("用户未登录!无法获取用户信息");
			return null;
		}
		try {
			JwtEntity user = (JwtEntity) (SecurityUtils.getSubject().getPrincipal());
			return user;
		} catch (Exception e) {
			log.error("无法获取用户信息!");
			return null;
		}


	}
}
