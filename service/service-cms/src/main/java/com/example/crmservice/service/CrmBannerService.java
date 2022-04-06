package com.example.crmservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.crmservice.entity.CrmBanner;
import com.example.crmservice.entity.vo.BannerQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.example.cmsservice.service
 *
 * @author xzwnp
 * 2022/3/22
 * 11:06
 * Steps：
 */
public interface CrmBannerService extends IService<CrmBanner> {

    IPage<CrmBanner> pageBanner(Page<CrmBanner> pageParam, BannerQuery bannerQuery);

    CrmBanner getBannerById(String id);

    void saveBanner(CrmBanner banner);

    void updateBannerById(CrmBanner banner);

    void removeBannerById(String id);

    List<CrmBanner> selectIndexList();
}
