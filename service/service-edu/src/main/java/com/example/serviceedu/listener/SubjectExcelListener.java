package com.example.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.serviceedu.entity.EduSubject;
import com.example.serviceedu.entity.SubjectExcelData;
import com.example.serviceedu.service.EduSubjectService;

/**
 * com.example.serviceedu.listener
 *
 * @author xzwnp
 * 2022/1/29
 * 23:31
 * Steps：
 */

public class SubjectExcelListener extends AnalysisEventListener<SubjectExcelData> {
    EduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectExcelData data, AnalysisContext analysisContext) {
        //查找一级课程是否存在,如果不存在,就新增
        String firstCourseName = data.getFirstCourse();
        EduSubject firstCourse = getCourse(firstCourseName, "0");
        if (firstCourse == null){
            firstCourse = new EduSubject(firstCourseName,"0");
            //结果为null,就新增
            subjectService.save(firstCourse);
            //保存时会把主键等信息填充到EduSubject对象中
        }
        String pid = firstCourse.getId();
        //查找二级课程是否存在,如果不存在,就新增
        String secondCourseName = data.getSecondCourse();
        if (getCourse(secondCourseName, pid)==null){
            subjectService.save(new EduSubject(secondCourseName,pid));
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("全部完成");
    }


    /**
     * 根据课程标题和pid查询某个课程是否存在,如果不存在,就返回null
     * @return id
     */
    public EduSubject getCourse(String childCourseName, String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",childCourseName);
        wrapper.eq("parent_id",pid);
        return subjectService.getOne(wrapper);
    }

}
