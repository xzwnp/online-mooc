package com.example.staservice.controller;


import com.example.commonutils.R;
import com.example.staservice.entity.StatisticsDaily;
import com.example.staservice.service.StatisticsDailyService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 *
 */
@RestController
@RequestMapping("/staservice/sta")
public class StatisticsDailyController {
    @Autowired
    StatisticsDailyService dailyService;

    @PostMapping("{day}")
    public R createStatisticsByDate(@PathVariable String day) {
        System.out.println("正在生成日报");
        return R.ok();
    }

    @GetMapping("showChart/{begin}/{end}/{type}")
	@ApiOperation("生成指定类型的次数统计")
    public R showChart(@PathVariable String begin, @PathVariable String end, @PathVariable String type) {
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return R.ok().data(map);
    }


}

