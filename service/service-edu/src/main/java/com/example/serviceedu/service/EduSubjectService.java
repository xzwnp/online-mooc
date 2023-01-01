package com.example.serviceedu.service;

import com.example.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.serviceedu.entity.subject.PrimaryClassification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *

 * 
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file);

    List<PrimaryClassification> getAllSubject();
}
