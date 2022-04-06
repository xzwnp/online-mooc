package com.example.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.JwtUtils;
import com.example.educenter.entity.LoginVo;
import com.example.educenter.entity.RegisterVo;
import com.example.educenter.entity.UcenterMember;
import com.example.educenter.mapper.UcenterMemberMapper;
import com.example.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.educenter.util.HttpClientUtils;
import com.example.educenter.util.WxPropertiesUtil;
import com.example.servicebase.exception.GuliException;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import org.springframework.util.DigestUtils;

import java.util.HashMap;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
@Service
@Slf4j
public class UcenterMemberServiceImpl
        extends ServiceImpl<UcenterMemberMapper, UcenterMember>
        implements UcenterMemberService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //校验参数
        if (StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(mobile)) {
            throw new GuliException(20001, "请检查输入!");
        }

        //获取会员
        UcenterMember member = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (null == member) {
            throw new GuliException(20001, "用户不存在!");
        }

        //校验密码
        if (!DigestUtils.md5DigestAsHex(loginVo.getPassword().getBytes()).equals(member.getPassword())) {
            throw new GuliException(20001, "密码错误!");
        }

        //校验是否被禁用
        if (member.getIsDisabled()) {
            throw new GuliException(20001, "error");
        }

        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return token;
    }

    /**
     * 会员注册
     *
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code)) {
            throw new GuliException(20001, "请检查输入!");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (count > 0) {
            throw new GuliException(20001, "该手机号已被注册!");
        }
        //校验验证码
        //从redis获取发送的验证码
        String verificationCode = ObjectUtils.nullSafeToString(redisTemplate.opsForValue().get("VerificationCode::" + mobile));
        if ("null".equals(verificationCode)) {
            throw new GuliException(20001, "验证码已失效!");
        }
        if (!code.equals(verificationCode)) {
            throw new GuliException(20001, "验证码不匹配!");
        }

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(registerVo.getMobile());
        member.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        try {
            this.save(member);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "注册失败!");
        }
        log.info("注册成功!");
    }

    @Override
    public String loginByWeChat(String code) {
        //1.通过code获取access_token和open_id
        //注意:code的超时时间为10分钟，一个code只能成功换取一次access_token即失效。code的临时性和一次保障了微信授权登录的安全性。

        //这里grant_type直接写死为authorization_code就行
        String urlForAccessToken = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        urlForAccessToken = String.format(urlForAccessToken
                , WxPropertiesUtil.WX_OPEN_APP_ID
                , WxPropertiesUtil.WX_OPEN_APP_SECRET, code);
        String responseBody = null;
        //发送get请求到微信官方提供的url
        try {
            responseBody = HttpClientUtils.get(urlForAccessToken);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "获取AccessToken失败!");
        }
        //结果被HttpClientUtils转成了String类型,现在把它转回json类型
        Gson gson = new Gson();
        //该方法会把结果保存到map
        HashMap map = gson.fromJson(responseBody, HashMap.class);
        String accessToken = (String) map.get("access_token");
        String openid = (String) map.get("openid");

        //2.使用accessToken获取用户信息
        String urlForUserInfo = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token=%s" +
                "&openid=%s";
        log.info("accessToken=" + accessToken);
        urlForUserInfo = String.format(urlForUserInfo, accessToken, openid);
        String responseBodyOfUserInfo;
        try {
            //废物利用
            responseBodyOfUserInfo = HttpClientUtils.get(urlForUserInfo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "获取用户信息失败!");
        }
        //该方法会把结果保存到map
        HashMap<String, String> userInfoMap = gson.fromJson(responseBodyOfUserInfo, HashMap.class);
        //3.如果用户不存在,就注册.把用户信息存储到数据库
        UcenterMember member = this.getMemberByOpenid(openid);
        if (this.getMemberByOpenid(openid) == null) {
            member = new UcenterMember();
            member.setOpenid(userInfoMap.get("openid"));
            //头像这里最好先把头像存储到对象存储,再保存新url到数据库
            member.setAvatar(userInfoMap.get("headimgurl"));
            member.setNickname(userInfoMap.get("nickname"));
            baseMapper.insert(member);
        }
        //4.返回token
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    @Override
    public UcenterMember getMemberByOpenid(String openid) {
        return baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("openid", openid));
    }

    @Override
    public Integer countRegisterByDay(String day) {
        return baseMapper.countByGmtCreateDaily(day);
    }
}
