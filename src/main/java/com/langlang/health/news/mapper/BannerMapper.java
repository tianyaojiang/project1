package com.langlang.health.news.mapper;

import com.langlang.health.news.entity.Banner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by tyj on 2019/01/08.
 */
@Mapper
public interface BannerMapper {

    List<Banner> getBannerList();
}
