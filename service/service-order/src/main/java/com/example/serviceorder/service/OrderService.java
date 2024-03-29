package com.example.serviceorder.service;

import com.example.serviceorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.commonutils.vo.SeckillCourseOrder;

/**
 * <p>
 * 订单 服务类
 * </p>
 *

 * 
 */
public interface OrderService extends IService<Order> {
    String saveOrder(String courseId, String userId);

    void saveSeckillOrder(SeckillCourseOrder orderInfo);
}
