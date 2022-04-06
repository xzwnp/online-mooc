package com.example.serviceedu.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * com.example.serviceedu.entity
 *
 * @author xzwnp
 * 2022/1/30
 * 11:34
 * Steps：
 */
@Data
public class SubjectExcelData {
    /**
     * 一级课程
     */
    @ExcelProperty(index = 0)
    private String firstCourse;

    @ExcelProperty(index = 1)
    private String secondCourse;
}
