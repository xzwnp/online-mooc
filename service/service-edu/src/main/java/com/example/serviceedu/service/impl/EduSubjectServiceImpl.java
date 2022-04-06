package com.example.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.servicebase.exception.GuliException;
import com.example.serviceedu.entity.EduSubject;
import com.example.serviceedu.entity.SubjectExcelData;
import com.example.serviceedu.entity.subject.PrimaryClassification;
import com.example.serviceedu.entity.subject.SecondaryClassification;
import com.example.serviceedu.listener.SubjectExcelListener;
import com.example.serviceedu.mapper.EduSubjectMapper;
import com.example.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2022-01-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void addSubject(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectExcelData.class, new SubjectExcelListener(this)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001, "读取文件流失败!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "保存失败!");
        }
    }

    @Override
    public List<PrimaryClassification> getAllSubject() {
        //1.从数据库查出所有一级课程信息
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", 0);
        List<EduSubject> listOne = baseMapper.selectList(wrapperOne);

        //2.从数据库查出所有二级课程信息
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> listTwo = baseMapper.selectList(wrapperTwo);

        List<PrimaryClassification> resultList = new ArrayList<>();
        //3.创建一级课程对象,加入list
        for (EduSubject subjectOne : listOne) {
            PrimaryClassification primary = new PrimaryClassification();
            String pid =subjectOne.getId();

            primary.setId(pid);
            primary.setTitle(subjectOne.getTitle());
            //寻找符合条件的二级课程对象,加入一级课程对象的childrenList
            for (EduSubject subjectTwo : listTwo) {
                if (pid.equals(subjectTwo.getParentId())){
                    primary.getChildren().add(new SecondaryClassification(subjectTwo.getId(),subjectTwo.getTitle()));
                }
            }
            //把一级课程加入返回list
            resultList.add(primary);
        }

        //4.返回
        return resultList;
    }


}
