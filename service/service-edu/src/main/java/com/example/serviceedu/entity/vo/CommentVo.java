package com.example.serviceedu.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.serviceedu.entity.EduComment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * com.example.serviceedu.entity.vo
 *
 * @author xzwnp
 * 2022/12/31
 * 12:52
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "EduComment对象", description = "课程评论 以课程小节为单位")
@NoArgsConstructor
public class CommentVo {

	@TableId(value = "id", type = IdType.ID_WORKER_STR)
	private Integer id;

	@ApiModelProperty(value = "对应的课程/视频资料编号")
	private String videoId;

	@ApiModelProperty(value = "评论内容")
	private String content;

	@ApiModelProperty(value = "设计为二级评论")
	private Integer pid;

	@ApiModelProperty("发送者昵称")
	private String senderNickname;

	@ApiModelProperty(value = "发送者头像")
	private String senderAvatar;

	@ApiModelProperty(value = "回复对象昵称")
	private String replyTo;

	@ApiModelProperty(value = "创建时间")
	private Date gmtCreate;

	@ApiModelProperty(value = "更新时间")
	private Date gmtModified;

	@ApiModelProperty(value = "子评论(楼中楼)")
	private List<CommentVo> children = new ArrayList<>();

	public CommentVo(EduComment comment) {
		BeanUtils.copyProperties(comment, this);
	}

	/**
	 * 懒加载
	 */
	public void addChild(CommentVo child) {
		if (children == null) {
			children = new ArrayList<>();
		}
		children.add(child);
	}
}