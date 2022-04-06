package com.example.serviceedu.EasyExcelDemo.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * com.example.eduservice.EasyExcelDemo.listener
 *
 * @author xzwnp
 * 2022/1/29
 * 15:15
 * Steps：
 */
public class MyReadListener<T> extends AnalysisEventListener<T> {
    private List<T> list;
    private Map<Integer,String> headMap;
    
    public MyReadListener() {
        list = new ArrayList<>();
        headMap = new HashMap<>();
    }

    /**
     * 该函数会多次被调用,一次读一行,
     * 每次把读取到的结果封装到T对象中
     * @param t
     * @param analysisContext
     */
    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        System.out.println("****"+t);
        list.add(t);
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息：" + headMap);
        this.headMap = headMap;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    /**
     * 获取读到的结果集
     */
    public List<T> getResultList() {
        return list;
    }

    /**
     * 获取表头信息
     */
    public Map<Integer, String> getHeadMap() {
        return headMap;
    }
}
