package com.example.serviceedu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * com.example.serviceedu.entity.vo
 *
 * @author xzwnp
 * 2022/1/27
 * 10:45
 * VO（View Object）：视图对象，它的作用是把某个指定页面（或组件）的所有数据封装起来。
 * 相对于使用Map,更推荐使用vo来封装整个表单数据
 */
@ApiModel(value = "Teacher查询条件", description = "讲师查询对象封装")
@Data
public class TeacherQuery {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "教师名称,模糊查询")
	private String name;

	@ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
	private Integer level;

	@ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
	//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换
	//日期格式应当为yyyy-MM-dd或yyyy-MM-dd HH:mm:ss
	private String begin;

	@ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
	private String end;
}
