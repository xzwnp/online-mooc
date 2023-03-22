package com.example.seckill.service;

import com.example.seckill.entity.SeckillCourse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-15
 */
public interface SeckillCourseService extends IService<SeckillCourse> {

	void shelf();

	String getSeckillCourseKey(String courseId);

	List<SeckillCourse> getSeckillInfo();

	void doSeckill(Integer seckillId, String key);

	void clearExpireSeckillCourse();
}
