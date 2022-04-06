package com.example.commonutils.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * com.example.commonutils.vo
 *
 * @author xzwnp
 * 2022/4/1
 * 22:21
 * 生成订单信息时所需的课程信息
 */

@Data
public class CourseOrderVo {
    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    // 0.01
    private BigDecimal price;

    @ApiModelProperty(value = "讲师名称")
    private String teacherName;

}
