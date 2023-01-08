package com.example.staservice.schedule;

import com.example.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * com.example.staservice.schedule
 *
 * @author xzwnp
 * 2022/4/3
 * 13:46
 * Steps：
 */
@Component
public class Task1 {
	@Autowired
	StatisticsDailyService dailyService;

	/**
	 * 每天凌晨五点执行一次,生成登录数,注册数,观看次数的统计信息
	 */
	@Scheduled(cron = "0 0 5 * * ? ")
	public void task1() {
		String day = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		//由于本网站为实验室产品,跑定时任务效果太差,数据为假数据
//		dailyService.createStatisticsByDay(day);

	}

}
