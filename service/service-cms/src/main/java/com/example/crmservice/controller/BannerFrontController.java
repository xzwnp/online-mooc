package com.example.crmservice.controller;

import com.example.commonutils.R;
import com.example.crmservice.entity.CrmBanner;
import com.example.crmservice.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.example.crmservice.controller
 *
 * @author xzwnp
 * 2022/3/22
 * 21:10
 * Steps：
 */
@Api("前台调用")
@RestController
@RequestMapping("/educms/banner")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    public R index() {
        List<CrmBanner> list = bannerService.selectIndexList();
        return R.ok().data("bannerList", list);
    }

}
