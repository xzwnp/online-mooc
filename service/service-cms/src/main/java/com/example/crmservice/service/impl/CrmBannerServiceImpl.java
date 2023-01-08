package com.example.crmservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.crmservice.entity.CrmBanner;
import com.example.crmservice.entity.vo.BannerQuery;
import com.example.crmservice.mapper.CrmBannerMapper;
import com.example.crmservice.service.CrmBannerService;
import com.example.servicebase.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.example.crmservice.service.impl
 *
 * @author xzwnp
 * 2022/3/22
 * 11:34
 * Steps：
 */
@Service
@Slf4j
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    CacheManager cacheManager;

    @Override
//    @Cacheable(value = "banner", key = "#pageParam")
    public IPage<CrmBanner> pageBanner(Page<CrmBanner> pageParam, BannerQuery query) {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
            baseMapper.selectPage(pageParam, wrapper);
        return pageParam;
    }

    @Override
    @Cacheable(value = "banner", key = "#id")
    public CrmBanner getBannerById(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void saveBanner(CrmBanner banner) {
        Cache cache = cacheManager.getCache("banner");
        if (cache != null) {
            cache.put(banner.getId(), banner);
        } else {
            log.error("设置缓存失败!");
        }
        int result = baseMapper.insert(banner);
        if (result <= 0) {
            throw new GlobalException(20001, "保存失败!");
        }
        ;
    }

    @Override
    public void updateBannerById(CrmBanner banner) {
        Cache cache = cacheManager.getCache("banner");
        if (cache != null) {
            cache.put(banner.getId(), banner);
        } else {
            log.error("设置缓存失败!");
        }
        if (baseMapper.updateById(banner) <= 0) {
            throw new GlobalException(20001, "更新失败!");
        }
        ;
    }

    @CacheEvict(value = "banner", key = "#id")
    @Override
    public void removeBannerById(String id) {
        if (baseMapper.deleteById(id) <= 0) {
            throw new GlobalException(20001, "删除失败!");
        }
    }

    @Override
    @Cacheable(value = "banner",key = "'list'")
    public List<CrmBanner> selectIndexList() {
            return baseMapper.selectList(null);
    }
}
