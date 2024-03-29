package com.example.serviceorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.commonutils.JwtEntity;
import com.example.commonutils.JwtUtil;
import com.example.commonutils.R;
import com.example.serviceorder.entity.Order;
import com.example.serviceorder.service.OrderService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *

 * 
 */
@RestController
@RequestMapping("/orderservice/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //根据课程id和用户id创建订单，返回订单id
    @GetMapping("createOrder/{courseId}")
    public R save(@PathVariable String courseId) {
		JwtEntity jwtEntity = (JwtEntity) (SecurityUtils.getSubject().getPrincipal());
		String memberId = jwtEntity.getUserId();
		if (StringUtils.isEmpty(memberId)) {
            return R.error().message("登录过期!请重新登录");
        }
        String orderId = orderService.saveOrder(courseId, memberId);
        return R.ok().data("orderId", orderId);
    }

    @GetMapping("getOrder/{orderId}")
    public R get(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }
}

