package com.example.seckill.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
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
@TableName("seckill_session")
@ApiModel(value = "秒杀场次", description = "")
public class SeckillSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type =IdType.AUTO)
    private Integer seckillSessionId;

    @ApiModelProperty(value = "秒杀开始时间")
    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
