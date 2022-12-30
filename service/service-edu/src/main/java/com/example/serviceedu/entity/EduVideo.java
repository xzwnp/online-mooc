package com.example.serviceedu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程视频
 * </p>
 *
 * @author atguigu
 * @since 2022-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "课程视频/课程资料", description = "课程视频")
public class EduVideo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "视频/资料ID")
	@TableId(value = "id", type = IdType.ID_WORKER_STR)
	private String id;
	@ApiModelProperty(value = "类型:1视频,2:PDF")
	private Integer type;

	@ApiModelProperty(value = "课程ID")
	private String courseId;

	@ApiModelProperty(value = "章节ID")
	private String chapterId;

	@ApiModelProperty(value = "节点名称")
	private String title;

	@ApiModelProperty(value = "视频/资料url")
	private String videoSourceId;

	@ApiModelProperty(value = "原始文件名称")
	private String videoOriginalName;

	@ApiModelProperty(value = "排序字段")
	private Integer sort;

	@ApiModelProperty(value = "播放次数")
	private Long playCount;

	@ApiModelProperty(value = "是否可以试听：0收费 1免费")
	private Integer isFree;

	@ApiModelProperty(value = "视频时长（秒）")
	private Float duration;

	@ApiModelProperty(value = "Empty未上传 Transcoding转码中  Normal正常")
	private String status;

	@ApiModelProperty(value = "视频/资料源文件大小（字节）")
	private Long size;

	@ApiModelProperty(value = "乐观锁")
	private Long version;

	@ApiModelProperty(value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date gmtCreate;

	@ApiModelProperty(value = "更新时间")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date gmtModified;


}
