package com.example.serviceedu.mapperTest;

import com.example.serviceedu.entity.EduSubject;
import com.example.serviceedu.service.EduSubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * com.example.serviceedu.mapperTest
 *
 * @author xzwnp
 * 2022/1/30
 * 12:53
 * Steps：
 */
@SpringBootTest
public class SubjectMapperTest {
    @Autowired
    EduSubjectService eduSubjectService;

    @Test
    public void saveTest() {
        EduSubject subject = new EduSubject("测试课程", "0");
        System.out.println("保存之前" + subject.getId());
        eduSubjectService.save(subject);
        System.out.println("保存之后" + subject.getId());
    }


}
