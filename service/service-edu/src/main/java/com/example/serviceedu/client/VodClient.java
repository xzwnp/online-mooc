package com.example.serviceedu.client;

import com.example.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * com.example.serviceedu
 *
 * @author xzwnp
 * 2022/3/13
 * 15:54
 * Steps：
 */
@FeignClient(name="service-vod",fallback = VodFileDegradeFeignClient.class) //指明调的是哪个服务
@Component //注册为spring组件
public interface VodClient {
    //写的是被调用的mapping的相对路径
    @DeleteMapping("eduvod/video/removeAlyVideo/{id}")
    R removeVideo(@PathVariable("id") String id);
    @DeleteMapping("eduvod/video/removeAlyVideobatch/{id}")
    R removeVideoBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
