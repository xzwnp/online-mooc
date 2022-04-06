package com.example.educenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.JwtUtils;
import com.example.educenter.entity.LoginVo;
import com.example.educenter.entity.RegisterVo;
import com.example.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.servicebase.exception.GuliException;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    String login(LoginVo loginVo);
    void register(RegisterVo registerVo);
    String loginByWeChat(String code);
    UcenterMember getMemberByOpenid(String openid);

    Integer countRegisterByDay(String day);
}
