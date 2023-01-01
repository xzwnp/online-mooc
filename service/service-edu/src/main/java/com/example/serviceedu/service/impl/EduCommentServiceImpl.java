package com.example.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commonutils.vo.UserInfoOrderVo;
import com.example.serviceedu.client.UCenterClient;
import com.example.serviceedu.entity.EduComment;
import com.example.serviceedu.entity.form.CommentForm;
import com.example.serviceedu.entity.vo.CommentVo;
import com.example.serviceedu.mapper.EduCommentMapper;
import com.example.serviceedu.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程评论 以课程小节为单位 服务实现类
 * </p>
 *
 * @author xiaozhiwei
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {
	@Autowired
	UCenterClient uCenterClient;


	@Override
	public Page<CommentVo> treeCommentPage(String videoId, int current, int size) {
		//先分页查全部pid为null的评论
		Page<EduComment> page = new Page<>(current, size);
		LambdaQueryWrapper<EduComment> parentWrapper = new LambdaQueryWrapper<>();
		parentWrapper.eq(EduComment::getVideoId, videoId);
		parentWrapper.isNull(EduComment::getPid);
		List<EduComment> parentList = this.page(page, parentWrapper).getRecords();

		//再去查所有二级评论,这里避免循坏查表
		List<Integer> pidList = parentList.stream().map(EduComment::getId).collect(Collectors.toList());
		LambdaQueryWrapper<EduComment> childWrapper = new LambdaQueryWrapper<>();
		childWrapper.eq(EduComment::getVideoId, videoId);
		childWrapper.in(EduComment::getPid, pidList);
		//todo 每个评论的二级评论只查前10条,多余的需要点击查看详情才会加载
		List<EduComment> children = this.list(childWrapper);
		//最后组装评论
		Map<Integer, CommentVo> commentVoMap = parentList.stream().map(CommentVo::new)
			.collect(Collectors.toMap(CommentVo::getId, commentVo -> commentVo));
		children.stream().forEach((child) -> {
			commentVoMap.get(child.getPid()).addChild(new CommentVo(child));
		});



		Page<CommentVo> result = new Page<>();
		result.setRecords(new ArrayList<>(commentVoMap.values()));
		result.setTotal(page.getTotal());
		return result;
	}


	@Override
	public void createComment(CommentForm form) {
		//查询用户是否已经被禁言
		UserInfoOrderVo userInfo = uCenterClient.getInfo(form.getSenderId());
		Assert.isTrue(userInfo != null && !userInfo.getIsDisabled(), "您已被禁言!评论失败!");
		EduComment eduComment = new EduComment(form);
		Assert.isTrue(this.save(eduComment), "保存评论失败!");
	}
}
