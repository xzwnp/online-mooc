package com.example.serviceedu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * com.example.serviceedu.entity.vo
 *
 * @author xzwnp
 * 2022/3/28
 * 20:01
 * Steps：
 */
//封装了课程条件查询的条件
@ApiModel(value = "课程查询条件", description = "课程查询对象封装")
@Data
public class CourseQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

    @ApiModelProperty(value = "排序 1:上架日期 2:销量 3:价格")
    private String sort;

    @ApiModelProperty(value = "是否降序排序,默认为true")
    private boolean desc = true;
}
