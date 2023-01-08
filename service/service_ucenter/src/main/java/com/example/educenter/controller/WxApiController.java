package com.example.educenter.controller;

import com.example.educenter.service.UcenterMemberService;
import com.example.educenter.util.WxPropertiesUtil;
import com.example.servicebase.exception.GlobalException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * com.example.educenter.controller
 *
 * @author xzwnp
 * 2022/3/27
 * 16:32
 * Steps：
 */
@Controller
@Api("微信登录接口")
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
	@Autowired
	UcenterMemberService memberService;
    @GetMapping("login")
	public String genQrConnect(HttpSession session) {

		// 微信开放平台授权baseUrl
        //redirect_uri 成功登录后会跳转到哪个uri,但不能自由设定
        //response_type 扫描登录指定填code
        //scope 在哪些域生效,网站应用填snsapi_login就行
        //state 扫描成功后会把发过去的state原样返回,每次随机生成一个state,防止跨站攻击
		String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
				"?appid=%s" +
				"&redirect_uri=%s" +
				"&response_type=code" +
				"&scope=snsapi_login" +
				"&state=%s" +
				"#wechat_redirect";

		// 回调地址
		String redirectUrl = WxPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
		try {
			redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //微信官方要求使用URLEncoder对url编码
		} catch (UnsupportedEncodingException e) {
			throw new GlobalException(20001, e.getMessage());
		}

		// 防止csrf攻击（跨站请求伪造攻击）
		//String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
		String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
		// 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
		//键："wechar-open-state-" + httpServletRequest.getSession().getId()
		//值：satte
		//过期时间：30分钟

		//生成qrcodeUrl
		String qrcodeUrl = String.format(
				baseUrl,
				WxPropertiesUtil.WX_OPEN_APP_ID,
				redirectUrl,
				state);
        //需要加上redirect 相当于让客户端再请求一次这个url 不加url相当于直接返回界面
		return "redirect:" + qrcodeUrl;
	}

	/**
	 * 用户登录后自动调用本方法,获取code,再利用code获取accessToken和用户的openId
	 * @param code
	 * @param state
	 */
	@GetMapping("callback")
    public String callback(String code,String state) {
        //state是之前指定的,
        //code是登录成功后返回的一个随机生成的值
		String token = memberService.loginByWeChat(code);
		return "redirect:http://localhost:3000?token="+token;
    }
}
