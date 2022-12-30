package com.example.serviceedu.entity.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * com.example.serviceedu.entity.subject
 *
 * @author xzwnp
 * 2022/1/31
 * 11:07
 * Steps：
 */
@ApiModel("二级分类")
public class SecondaryClassification {
	@ApiModelProperty("二级分类id")
	private String id;
	@ApiModelProperty("二级分类名")
	private String title;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SecondaryClassification() {
	}

	public SecondaryClassification(String id, String title) {

		this.id = id;
		this.title = title;
	}
}
