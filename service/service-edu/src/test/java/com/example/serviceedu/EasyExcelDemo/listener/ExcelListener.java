package com.example.serviceedu.EasyExcelDemo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.serviceedu.EasyExcelDemo.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * com.example.eduservice.EasyExcelDemo.listener
 *
 * @author xzwnp
 * 2022/1/29
 * 15:01
 * 读取excel需要创建一个监听器类
 */
public class ExcelListener extends AnalysisEventListener<User> {
    public List<User> userList = new ArrayList<>();
    /**
     * 一行一行地读取内容,封装到user类中
     * @param user
     * @param analysisContext
     */
    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        System.out.println("***"+user);
        userList.add(user);
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("全部读完了");
    }
}
