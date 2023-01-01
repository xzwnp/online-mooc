package com.example.serviceedu.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.example.serviceedu.entity.form.CommentForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * 课程评论 以课程小节为单位
 * </p>
 *
 * @author xiaozhiwei
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "EduComment对象", description = "课程评论 以课程小节为单位")
@NoArgsConstructor
public class EduComment implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty(value = "对应的课程/视频资料编号")
	private String videoId;

	@ApiModelProperty(value = "评论内容")
	private String content;

	@ApiModelProperty(value = "设计为二级评论")
	private Integer pid;

	private String senderNickname;

	@ApiModelProperty(value = "发送者头像")
	private String senderAvatar;

	@ApiModelProperty(value = "回复对象昵称")
	private String replyTo;


	@ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
	@TableLogic
	private Integer isDeleted;

	@ApiModelProperty(value = "创建时间")
	@TableField(fill = FieldFill.INSERT)
	private Date gmtCreate;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新时间")
	private Date gmtModified;


	public EduComment(CommentForm form) {
		BeanUtils.copyProperties(form, this);
	}
}
