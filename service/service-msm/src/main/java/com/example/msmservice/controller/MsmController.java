package com.example.msmservice.controller;

import com.example.commonutils.R;
import com.example.msmservice.service.MsmService;
import com.mysql.cj.log.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.example.msmservice.controller
 *
 * @author xzwnp
 * 2022/3/25
 * 18:10
 * Steps：
 */
@RestController
@RequestMapping("/edumsm/msm/")
@Slf4j
@Api("短信服务")
public class MsmController {
    @Autowired
    MsmService msmService;
    @GetMapping("send/{phone}")
    public R sendMessage(@PathVariable String phone){
        if (phone.length()!=11){
            return R.error().message("手机号长度不对!");
        }
        String code = msmService.sendMessage(phone);
        log.info(code);
        return R.ok();
    }


}
