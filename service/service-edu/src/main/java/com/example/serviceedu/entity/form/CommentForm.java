package com.example.serviceedu.entity.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.serviceedu.entity.EduComment;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * com.example.serviceedu.entity.form
 *
 * @author xzwnp
 * 2022/12/31
 * 13:48
 */
@Data
public class CommentForm {

	@ApiModelProperty(value = "对应的课程/视频资料编号")
	private String videoId;

	@ApiModelProperty(value = "评论内容")
	private String content;

	@ApiModelProperty(value = "设计为二级评论")
	private Integer pid;

	@ApiModelProperty("发送者用户id")
	private String senderId;

	@ApiModelProperty("发送者昵称")
	private String senderNickname;

	@ApiModelProperty(value = "发送者头像")
	private String senderAvatar;

	@ApiModelProperty(value = "回复对象昵称")
	private String replyTo;

}
