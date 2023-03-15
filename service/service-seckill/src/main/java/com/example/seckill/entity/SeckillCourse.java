package com.example.seckill.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("seckill_course")
@ApiModel(value="SeckillCourse对象", description="")
public class SeckillCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "seckill_id", type = IdType.AUTO)
    private Integer seckillId;

    @ApiModelProperty(value = "秒杀开始时间")
    private Date startTime;

    private Date endTime;

    private String courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程封面链接")
    private String courseCover;

    @ApiModelProperty(value = "讲师名称")
    private String teacherName;

    @ApiModelProperty(value = "原价")
    private BigDecimal originPrice;

    @ApiModelProperty(value = "当前价格")
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "库存量")
    private Integer storeCount;


}
