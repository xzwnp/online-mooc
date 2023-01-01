package com.example.serviceedu.entity.chapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("小节信息")
public class VideoVo {

	@ApiModelProperty("小节id")
	private String id;
	@ApiModelProperty(value = "课程ID")
	private String courseId;

	@ApiModelProperty(value = "章节ID")
	private String chapterId;

	@ApiModelProperty("小节标题")
	private String title;

	@ApiModelProperty("小节类型,1视频 2PDF")
	private Integer type;

	@ApiModelProperty("该视频在阿里云视频点播上的id号")
	private String videoSourceId;

	@ApiModelProperty(value = "原始文件名称")
	private String videoOriginalName;

	@ApiModelProperty("是否免费")
	private boolean free;

	@ApiModelProperty("排序")
	private Integer sort;


}
