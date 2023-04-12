package com.example.serviceedu.client;

import com.example.commonutils.ResultCode;
import com.example.commonutils.vo.UserInfoOrderVo;
import com.example.servicebase.exception.GlobalException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.transform.Result;

/**
 * com.example.serviceorder.client
 *
 * @author xzwnp
 * 2022/4/2
 * 11:50
 * Steps：
 */
@FeignClient("service-ucenter")
public interface UCenterClient {
    Logger log = LoggerFactory.getLogger(UCenterClient.class);

    //根据课程id查询用户信息
    @GetMapping("/educenter/member/userOrderInfo/{id}")
    @CircuitBreaker(name = "default", fallbackMethod = "getUserFallback")
    //resilience4j提供的服务熔断,原理是这个方法是否抛异常了
    UserInfoOrderVo getInfo(@PathVariable("id") String id);

    default UserInfoOrderVo getUserFallback(String id, Exception e) {
        String message = String.format("用户服务-获取用户信息调用失败!用户id:%s原因:", id);
        log.error(message, e);
        throw new GlobalException(ResultCode.ERROR, "服务器错误!无法获取用户信息");
    }

}
