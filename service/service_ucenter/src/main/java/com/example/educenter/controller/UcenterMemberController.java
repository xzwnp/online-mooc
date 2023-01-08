package com.example.educenter.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.JwtEntity;
import com.example.commonutils.R;
import com.example.commonutils.vo.UserInfoOrderVo;
import com.example.educenter.entity.LoginVo;
import com.example.educenter.entity.RegisterVo;
import com.example.educenter.entity.UcenterMember;
import com.example.educenter.service.UcenterMemberService;
import io.jsonwebtoken.lang.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 会员表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/educenter/member")
@Api(tags = "会员相关接口")
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
	public R getMemberInfo() {
		JwtEntity jwtEntity = (JwtEntity) (SecurityUtils.getSubject().getPrincipal());
		String userId = jwtEntity.getUserId();
		UcenterMember member = memberService.getById(userId);
		return member == null ? R.error().message("获取用户信息失败!") : R.ok().data("userInfo", member);
	}

	@ApiOperation("获取会员信息")
	@GetMapping("userOrderInfo/{id}")
	public UserInfoOrderVo getInfo(@PathVariable String id) {
		//根据用户id获取用户信息
		UcenterMember ucenterMember = memberService.getById(id);
		Assert.isTrue(ucenterMember != null, "用户不存在!");
		UserInfoOrderVo member = new UserInfoOrderVo();
		BeanUtils.copyProperties(ucenterMember, member);
		return member;
	}

	@ApiOperation("获取当前管理员账号的信息")
	@RequiresAuthentication
	@GetMapping("adminInfo")
	public R getCurrentAdminInfo() {
		JwtEntity jwtEntity = (JwtEntity) (SecurityUtils.getSubject().getPrincipal());
		String id = jwtEntity.getUserId();
		//根据用户id获取用户信息
		UcenterMember ucenterMember = memberService.getById(id);
		Assert.isTrue(ucenterMember != null, "用户不存在!");
		return R.ok().data("name", ucenterMember.getNickname()).data("avatar", ucenterMember.getAvatar())
			.data("roles", jwtEntity.getRoles());
	}

	@GetMapping("current/{current}/size/{size}")
	public R getAll(@PathVariable int current, @PathVariable int size) {
		Page<UcenterMember> page = new Page<>(current, size);
		memberService.page(page, null);
		return R.ok().data("total", page.getTotal()).data("records", page.getRecords());
	}

	@ApiOperation("封禁用户")
	@PutMapping("ban/{id}")
	@RequiresRoles("sys_admin")
	public R banUser(@PathVariable String id) {
		//权限验证懒得做了,反正需求文档没说
		//根据用户id获取用户信息
		UcenterMember ucenterMember = new UcenterMember();
		ucenterMember.setId(id);
		ucenterMember.setIsDisabled(true);

		return memberService.updateById(ucenterMember) ? R.ok() : R.error().message("封禁失败");
	}

	@ApiOperation("解封用户")
	@PutMapping("release/{id}")
	@RequiresRoles("sys_admin")
	public R releaseUser(@PathVariable String id) {
		//权限验证懒得做了,反正需求文档没说
		//根据用户id获取用户信息
		UcenterMember ucenterMember = new UcenterMember();
		ucenterMember.setId(id);
		ucenterMember.setIsDisabled(false);

		return memberService.updateById(ucenterMember) ? R.ok() : R.error().message("解封成功");
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

