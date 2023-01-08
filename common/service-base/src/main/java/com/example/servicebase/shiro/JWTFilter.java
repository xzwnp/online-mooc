package com.example.servicebase.shiro;

import com.example.commonutils.JwtUtil;
import com.example.commonutils.R;
import com.example.servicebase.exception.GlobalException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码的执行流程 preHandle -> isAccessAllowed -> isLoginAttempt -> executeLogin
 */
@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {
	private ObjectMapper objectMapper = new ObjectMapper();


	/**
	 * 所有请求不管是否需要登录一律放行,如果有token就进行一下登录.
	 * 解释:
	 * 例如我们提供一个地址 GET /article
	 * 登入用户和游客看到的内容是不同的
	 * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
	 * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
	 * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可,避免硬编码导致的低灵活性和粗粒度
	 * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginAttempt(request, response)) {
			try {
				executeLogin(request, response);
			} catch (Exception e) {
				//登录失败,即token存在但无效
				out(request, response);
			}
		}
		return true;
	}

	/**
	 * 判断用户是否想要登入。
	 * 检测header里面是否包含Authorization字段即可
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		String authorization = req.getHeader(JwtUtil.TOKEN_HEADER);
		return authorization != null;
	}

	/**
	 *
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String authorization = httpServletRequest.getHeader(JwtUtil.TOKEN_HEADER);
		//检查token是否有效
		if (!authorization.startsWith(JwtUtil.TOKEN_PREFIX)) {
			throw new GlobalException(28004, "Token过期!");
		}
		BearerToken token = new BearerToken(authorization.substring(JwtUtil.TOKEN_PREFIX.length()), httpServletRequest.getRemoteHost());
		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		Subject subject = getSubject(request, response);
		subject.login(token);
		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}


	/**
	 * 对跨域提供支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//		httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

	/**
	 * 处理非法请求
	 */
	private void out(ServletRequest req, ServletResponse resp) {
		HttpServletResponse response = (HttpServletResponse) resp;
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			//自定义鉴权失败信息
			objectMapper.writeValue(outputStream, R.error().code(28004).message("用户信息过期或无效!"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		//response.setStatusCode(HttpStatus.UNAUTHORIZED);
		//指定编码，否则在浏览器中会中文乱码
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
	}
}
