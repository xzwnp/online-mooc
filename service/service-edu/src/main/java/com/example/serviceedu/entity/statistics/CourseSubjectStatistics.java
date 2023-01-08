package com.example.serviceedu.entity.statistics;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * com.example.serviceedu.entity.statistics
 *
 * @author xzwnp
 * 2023/1/3
 * 20:47
 */
@Data
public class CourseSubjectStatistics {
	@ApiModelProperty(value = "课程类别ID")
	private String subjectId;

	@ApiModelProperty(value = "类别名称")
	private String title;


	@ApiModelProperty("课程数")
	private Integer courseCount;

}
