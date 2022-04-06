package com.example.educenter.controller;


import com.example.commonutils.JwtUtils;
import com.example.commonutils.R;
import com.example.commonutils.vo.UserInfoOrderVo;
import com.example.educenter.entity.LoginVo;
import com.example.educenter.entity.RegisterVo;
import com.example.educenter.entity.UcenterMember;
import com.example.educenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-03-15
 */
@RestController
@RequestMapping("/educenter/member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = memberService.login(loginVo);
        return R.ok().data("token", token);
    }

    @ApiOperation(value = "会员注册")
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation("根据token获取会员信息")
    @GetMapping("info")
    public R getMemberInfo(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(id);
        return member == null ? R.error().message("获取用户信息失败!") : R.ok().data("userInfo", member);
    }

    @ApiOperation("获取生成订单信息所需的会员信息")
    @GetMapping("userOrderInfo/{id}")
    public UserInfoOrderVo getInfo(@PathVariable String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(id);
        UserInfoOrderVo member = new UserInfoOrderVo();
        BeanUtils.copyProperties(ucenterMember, member);
        return member;
    }

    /**
     * 查询某一天注册人数
     */
    @GetMapping(value = "countregister/{day}")
    public R registerCount(
            @PathVariable String day) {
        Integer count = memberService.countRegisterByDay(day);
        return R.ok().data("countRegister", count);
    }

}

