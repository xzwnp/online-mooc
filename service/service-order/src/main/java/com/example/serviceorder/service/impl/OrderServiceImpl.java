package com.example.serviceorder.service.impl;

import com.example.commonutils.vo.CourseOrderVo;
import com.example.commonutils.vo.UserInfoOrderVo;
import com.example.serviceorder.client.EduClient;
import com.example.serviceorder.client.UCenterClient;
import com.example.serviceorder.entity.Order;
import com.example.commonutils.vo.SeckillCourseOrder;
import com.example.serviceorder.mapper.OrderMapper;
import com.example.serviceorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.serviceorder.util.OrderNoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    EduClient eduClient;


    @Autowired
    //误报,不用管
    UCenterClient uCenterClient;

    //todo
    @Override
    public String saveOrder(String courseId, String userId) {
        UserInfoOrderVo userInfo = uCenterClient.getInfo(userId);
        log.info(userInfo.toString());
        CourseOrderVo courseInfo = eduClient.getInfo(courseId);
        log.info(courseInfo.toString());
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(userId);
        order.setMobile(userInfo.getMobile());
        order.setNickname(userInfo.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }

    @Override
    public void saveSeckillOrder(SeckillCourseOrder orderInfo) {
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(orderInfo.getCourseId());
        order.setCourseTitle(orderInfo.getCourseName());
        order.setCourseCover(orderInfo.getCourseCover());
        order.setTotalFee(orderInfo.getActualPrice());
        order.setMemberId(orderInfo.getBuyerId());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        //todo 推送消息让用户去支付
    }
}
