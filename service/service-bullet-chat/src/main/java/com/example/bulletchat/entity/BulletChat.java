package com.example.bulletchat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 弹幕
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bc_bullet_chat")
@ApiModel(value = "BulletChat对象", description = "弹幕")
@NoArgsConstructor
@AllArgsConstructor
public class BulletChat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "视频id")
    @TableField("videoId")
    private String videoId;

    @ApiModelProperty(value = "发送时间")
    @TableField("userId")
    private String userId;

    @ApiModelProperty(value = "弹幕内容")
    private String content;

    @ApiModelProperty(value = "弹幕在视频的时间戳")
    private Integer videoTimestamp;

    @ApiModelProperty(value = "点赞量")
    private Integer likeCount;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;


}
