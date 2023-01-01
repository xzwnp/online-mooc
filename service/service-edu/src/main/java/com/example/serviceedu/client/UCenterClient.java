package com.example.serviceedu.client;

import com.example.commonutils.vo.UserInfoOrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * com.example.serviceorder.client
 *
 * @author xzwnp
 * 2022/4/2
 * 11:50
 * Steps：
 */
@Component("ucenter")
@FeignClient("service-ucenter")
public interface UCenterClient {
    //根据课程id查询课程信息
    @GetMapping("/educenter/member/userOrderInfo/{id}")
    UserInfoOrderVo getInfo(@PathVariable("id") String id);


}
