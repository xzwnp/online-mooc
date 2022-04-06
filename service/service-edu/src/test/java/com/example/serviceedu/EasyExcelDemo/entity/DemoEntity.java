package com.example.serviceedu.EasyExcelDemo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
/**
 * com.example.eduservice.EasyExcelDemo
 *
 * @author xzwnp
 * 2022/1/29
 * 14:30
 * Steps：
 */
@Data
public class DemoEntity {
    //设置表头名称
    @ExcelProperty("学生编号")
    private int sno;

    //设置表头名称
    @ExcelProperty("学生姓名")
    private String sname;


}
