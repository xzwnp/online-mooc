package com.example.serviceedu.entity.chapter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("章节信息")
public class ChapterVo {

	@ApiModelProperty("章节id")
	private String id;
	@ApiModelProperty("章节标题")
	private String title;

	//表示小节
	@ApiModelProperty("小节")
	private List<VideoVo> children = new ArrayList<>();
}
