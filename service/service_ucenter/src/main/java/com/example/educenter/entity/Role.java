package com.example.educenter.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName role
 */
@TableName(value = "ucenter_role")
@Data
public class Role implements Serializable {
	/**
	 *
	 */
	@TableId
	private Integer id;

	/**
	 *
	 */
	private String role;

	/**
	 *
	 */
	private String description;

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

}