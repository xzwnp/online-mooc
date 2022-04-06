package com.example.serviceedu.client;

import com.example.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.example.serviceedu.client
 *
 * @author xzwnp
 * 2022/3/13
 * 18:20
 * Steps：
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    //当被访问的服务器宕机或超时时,会转而执行此方法
    @Override
    public R removeVideo(String id) {
        return R.error().message("time out");
    }
    @Override
    public R removeVideoBatch(List<String> videoIdList) {
        return R.error().message("time out");
    }
}
