package com.example.servicebase.shiro;

import com.example.commonutils.JwtEntity;
import com.example.commonutils.JwtUtil;
import com.example.servicebase.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author xiaozhiwei
 */
@Slf4j
@Component
public class JwtTokenRealm extends AuthorizingRealm {


	public JwtTokenRealm() {
		//CredentialsMatcher，自定义匹配策略（即验证jwt token的策略）
		super(new CredentialsMatcher() {
			@Override
			public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
				//我都能解析出来用户信息了,token还能是假的?
				return true;
				//如果要把token放redis的话,这一步就很有意义了
			}
		});
	}


	@Override//权限管理
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		JwtEntity user = (JwtEntity) SecurityUtils.getSubject().getPrincipal();
		log.info("权限验证,用户:{}", user.getUsername());
		log.info("用户具有如下角色:{}", user.getRoles());
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addRoles(user.getRoles());
		return simpleAuthorizationInfo;
	}

	/**
	 * 从token中解析出用户信息
	 */
	@Override
	public AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		log.info("获取token信息");
		BearerToken bearerToken = (BearerToken) authenticationToken;
		String bearerTokenString = bearerToken.getToken();
		//检查token是否有效
		if (!JwtUtil.checkToken(bearerTokenString)) {
			throw new GlobalException(28004, "token过期或无效,请重新登录!");
		}

		//解析出用户信息
		JwtEntity jwtEntity = JwtUtil.getUserInfo(bearerTokenString);
		//principal直接放对象,credential放token
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(jwtEntity, bearerTokenString, this.getName());
		return info;
	}


	@Override
	public String getName() {
		return "JwtTokenRealm";
	}

	@Override
	public Class getAuthenticationTokenClass() {
		//设置由本realm处理的token类型。BearerToken是在filter里自动装配的。
		return BearerToken.class;
	}


}
