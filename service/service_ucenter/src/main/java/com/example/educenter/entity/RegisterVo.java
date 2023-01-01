package com.example.educenter.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * com.example.educenter.entity
 *
 * @author xzwnp
 * 2022/3/24
 * 9:52
 * Steps：
 */
@Data
@ApiModel(value = "注册对象", description = "注册对象")
public class RegisterVo {
	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "头像")
	private String avatar;

}
