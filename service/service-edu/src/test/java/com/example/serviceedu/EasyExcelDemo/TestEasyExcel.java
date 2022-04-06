package com.example.serviceedu.EasyExcelDemo;

import com.alibaba.excel.EasyExcel;
import com.example.serviceedu.EasyExcelDemo.entity.DemoEntity;
import com.example.serviceedu.EasyExcelDemo.entity.User;
import com.example.serviceedu.EasyExcelDemo.listener.ExcelListener;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * com.example.eduservice.EasyExcelDemo
 *
 * @author xzwnp
 * 2022/1/29
 * 14:32
 * Steps：
 */
public class TestEasyExcel {
    @Test
    public void testWrite() {
        List<DemoEntity> studList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoEntity demoEntity = new DemoEntity();
            demoEntity.setSno(i);
            demoEntity.setSname("lucy" + i);
            studList.add(demoEntity);
        }
        EasyExcel.write("D:\\writeResult.xlsx", DemoEntity.class).sheet("第一页").doWrite(studList);

    }

    @Test
    public void testRead() {
        ExcelListener excelListener = new ExcelListener();
        String filename = "D:\\writeResult.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(filename, User.class, excelListener).sheet().doRead();
        System.out.println(excelListener.userList);

    }
}
