package com.example.serviceedu.mapperTest;

import com.example.serviceedu.mapper.EduCourseMapper;
import com.example.serviceedu.mapper.EduTeacherMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * com.example.serviceedu.mapperTest
 *
 * @author xzwnp
 * 2022/3/29
 * 21:58
 * Steps：
 */
@SpringBootTest
public class CourseMapperTest {
    @Autowired
    EduCourseMapper eduCourseMapper;
    @Autowired
    EduTeacherMapper eduTeacherMapper;
//    @Test
//    public void test(){
//        HashMap<String, Object> map = eduCourseMapper.testSql("14");
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println(entry.getKey()+entry.getValue());
//        }
//    }
    @Test
    public void test2(){
        eduTeacherMapper.selectList(null);
    }
}
