package com.example.staservice.controller;


import com.example.commonutils.R;
import com.example.staservice.entity.StatisticsDaily;
import com.example.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    StatisticsDailyService dailyService;

    @PostMapping("{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        System.out.println("正在生成日报");
        dailyService.createStatisticsByDay(day);
        return R.ok();
    }

    @GetMapping("showChart/{begin}/{end}/{type}")
    public R showChart(@PathVariable String begin, @PathVariable String end, @PathVariable String type) {
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }
}

