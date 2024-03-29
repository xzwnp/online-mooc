package com.example.serviceedu.entity.subject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * com.example.serviceedu.entity.subject
 *
 * @author xzwnp
 * 2022/1/31
 * 11:04
 * 一级分类
 */
@ApiModel("课程一级分类")
public class PrimaryClassification {
	@ApiModelProperty("id")
    private String id;
	@ApiModelProperty("分类名称")
    private String title;
	@ApiModelProperty("子分类")
    private List<SecondaryClassification> children;

    public PrimaryClassification() {
        children = new ArrayList<>();
    }

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

    public List<SecondaryClassification> getChildren() {
        return children;
    }

    public void setChildren(List<SecondaryClassification> children) {
        this.children = children;
    }
}
