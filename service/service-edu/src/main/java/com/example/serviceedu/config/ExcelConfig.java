package com.example.serviceedu.config;

import com.example.serviceedu.listener.SubjectExcelListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * com.example.serviceedu.config
 *
 * @author xzwnp
 * 2022/1/29
 * 23:30
 * Steps：
 */
@Configuration
public class ExcelConfig {
    @Bean
    @Scope("prototype")
    public SubjectExcelListener subjectExcelListener(){
        return new SubjectExcelListener();
    }
}
