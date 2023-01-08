package com.example.educenter.service;

import com.example.educenter.entity.LoginVo;
import com.example.educenter.entity.RegisterVo;
import com.example.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *

 *
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    String login(LoginVo loginVo);

	void register(RegisterVo registerVo);
    String loginByWeChat(String code);
    UcenterMember getMemberByOpenid(String openid);

    Integer countRegisterByDay(String day);
}
