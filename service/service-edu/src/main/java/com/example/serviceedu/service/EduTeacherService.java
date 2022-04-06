package com.example.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.serviceedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.serviceedu.entity.vo.TeacherQuery;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author atguigu
 * @since 2022-01-23
 */
public interface EduTeacherService extends IService<EduTeacher> {
    Page<EduTeacher> teacherPage(Page<EduTeacher> page, TeacherQuery teacherQuery);
    /**
     * 获取最热的前4名讲师,并加入缓存
     */
    List<EduTeacher> listCache() ;
}
