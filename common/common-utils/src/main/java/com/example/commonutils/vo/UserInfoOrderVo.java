package com.example.commonutils.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * com.example.commonutils.vo
 *
 * @author xzwnp
 * 2022/4/1
 * 22:26
 * Steps：
 */
@Data
public class UserInfoOrderVo {
	@ApiModelProperty(value = "会员id")
	@TableId(value = "id", type = IdType.ID_WORKER_STR)
	private String id;

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "是否禁用 1（true）已禁用，  0（false）未禁用")
	private Boolean isDisabled;
}
