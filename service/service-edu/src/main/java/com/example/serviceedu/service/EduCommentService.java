package com.example.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.serviceedu.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.serviceedu.entity.form.CommentForm;
import com.example.serviceedu.entity.vo.CommentVo;

/**
 * <p>
 * 课程评论 以课程小节为单位 服务类
 * </p>
 *
 * @author xiaozhiwei
 * 
 */
public interface EduCommentService extends IService<EduComment> {

    Page<CommentVo> treeCommentPage(String videoId, int current, int size);

	void createComment(CommentForm form);
}
