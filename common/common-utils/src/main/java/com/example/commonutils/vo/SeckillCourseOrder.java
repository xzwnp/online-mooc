package com.example.commonutils.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@ApiModel(value = "SeckillCourse对象", description = "")
public class SeckillCourseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer seckillId;
    @ApiModelProperty(value = "秒杀场次id")
    private Integer seckillSessionId;
    private String courseId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程封面链接")
    private String courseCover;

    @ApiModelProperty("买家id")
    private String buyerId;
    @ApiModelProperty("交易时间")
    private LocalDateTime transactionTime;

    @ApiModelProperty("实际购买价格")
    private BigDecimal actualPrice;


}
