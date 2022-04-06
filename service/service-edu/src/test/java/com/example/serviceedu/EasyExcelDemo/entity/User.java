package com.example.serviceedu.EasyExcelDemo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * com.example.eduservice.EasyExcelDemo.entity
 *
 * @author xzwnp
 * 2022/1/29
 * 14:58
 * 读操作的测试类
 */
@Data
public class User {
    @ExcelProperty(index = 0)
    private int id;
    @ExcelProperty(index = 1)
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }
}
