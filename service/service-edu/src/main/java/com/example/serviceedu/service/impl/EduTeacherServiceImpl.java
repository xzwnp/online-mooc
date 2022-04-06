package com.example.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.serviceedu.entity.EduTeacher;
import com.example.serviceedu.entity.vo.TeacherQuery;
import com.example.serviceedu.mapper.EduTeacherMapper;
import com.example.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.Vlookup;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-01-23
 */
@Service
@Slf4j
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Page<EduTeacher> teacherPage(Page<EduTeacher> page, TeacherQuery teacherQuery) {
        if (teacherQuery == null){
			return (Page<EduTeacher>) baseMapper.selectPage(page, null);
		}
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        log.info(teacherQuery.getName()+teacherQuery.getLevel()+teacherQuery.getBegin());
        if (!ObjectUtils.isEmpty(teacherQuery.getName())) {
            wrapper.like("name", teacherQuery.getName());

        }
        if (!ObjectUtils.isEmpty(teacherQuery.getLevel())) {
            wrapper.eq("level", teacherQuery.getLevel());
        }
        if (!ObjectUtils.isEmpty(teacherQuery.getBegin())) {
            wrapper.ge("gmt_create", teacherQuery.getBegin());
        }
        if (!ObjectUtils.isEmpty(teacherQuery.getEnd())) {
            wrapper.le("gmt_create", teacherQuery.getEnd());
        }
        baseMapper.selectPage(page, wrapper);
        return page;
    }

    @Override
    @Cacheable(value = "index",key = "'teacherList'")
    public List<EduTeacher> listCache() {
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        //暂时算法计算最热的名师,先用获取id最大的4位名师顶着
        teacherWrapper.orderByDesc("id");
        //last:拼接到sql语句的
        teacherWrapper.last("limit 4");
        return baseMapper.selectList(teacherWrapper);
    }
}
