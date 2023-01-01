package com.example.staservice.client;

import com.example.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * com.example.staservice.client
 *
 * @author xzwnp
 * 2022/4/2
 * 20:44
 * Steps：
 */
@FeignClient(value = "service-ucenter",fallback = UcenterClientFallBack.class)
@Component
public interface UcenterClient {
    @GetMapping(value = "educenter/member/countregister/{day}")
    R registerCount(@PathVariable String day);
}
