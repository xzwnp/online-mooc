package com.example.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.seckill.entity.SeckillCourse;
import com.example.seckill.mapper.SeckillCourseMapper;
import com.example.seckill.service.SeckillCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xiaozhiwei
 * @since 2023-03-15
 */
@Service
public class SeckillCourseServiceImpl extends ServiceImpl<SeckillCourseMapper, SeckillCourse> implements SeckillCourseService {
    private final String SECKILL_PREFIX = "seckill";
    private final String SECKILL_ = "seckill";
    private final String salt = "hgka$syf83wlfnZ.,.221hksd";

    @Override
    public void shelf() {
        //查询最近三天所有参与秒杀的商品
        LambdaQueryWrapper<SeckillCourse> wrapper = new LambdaQueryWrapper<>();
        Date date = new Date();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ddl = now.plusDays(3);
        wrapper.ge(SeckillCourse::getStartTime, now);
        wrapper.le(SeckillCourse::getStartTime, ddl);
        //按场次排序
        wrapper.orderByAsc(SeckillCourse::getStartTime);
        List<SeckillCourse> secKillCourses = baseMapper.selectList(wrapper);
        for (SeckillCourse course : secKillCourses) {
            //上架到对应的场次,如果已经存在就跳过
            LocalDateTime startTime = course.getStartTime();

        }
    }
}
