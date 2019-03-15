package com.langlang.health.news.service.impl;

import com.langlang.health.news.entity.Banner;
import com.langlang.health.news.mapper.BannerMapper;
import com.langlang.health.news.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tyj on 2018/08/15.
 */
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    BannerMapper bannerMapper;

    @Override
    public List<Banner> getBannerList() {
        return this.bannerMapper.getBannerList();
    }
}
