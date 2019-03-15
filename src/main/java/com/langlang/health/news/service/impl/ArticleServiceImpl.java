package com.langlang.health.news.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langlang.health.common.exception.HealthException;
import com.langlang.health.common.util.Tools;
import com.langlang.health.news.entity.Article;
import com.langlang.health.news.entity.Banner;
import com.langlang.health.news.mapper.ArticleMapper;
import com.langlang.health.news.mapper.BannerMapper;
import com.langlang.health.news.service.ArticleService;
import com.langlang.health.news.service.BannerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tyj on 2018/08/15.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public PageInfo<Article> getArticles(Integer p, Integer limit) {
        PageHelper.startPage(p, limit);
        List<Article> data = articleMapper.getArticleList();
        PageInfo<Article> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

    @Override
    @Cacheable(value = "aid",key="#id")
    public Article getArticlesById(Integer id) {
        if (StringUtils.isNotBlank( id.toString())) {
            if (Tools.isNumber(id.toString())) {
                Article article = articleMapper.getArticlesById(Integer.valueOf(id));
                if (article != null) {
                    article.setHits(article.getHits() + 1);
                    articleMapper.updateByPrimaryKey(article);
                }
                return article;
            }
        }
        return null;

    }






    @Override
    public void updateArticleByCid(Article article) {

    }

    @Override
    public PageInfo<Article> getArticles(Integer mid, int page, int limit) {
        return null;
    }

    @Override
    public PageInfo<Article> getArticles(String keyword) {
        return null;
    }



    @Override
    public PageInfo<Article> getArticlesWithpage(Article article, Integer page, Integer limit) {
        return null;
    }

    @Override
    public void deleteByCid(Integer cid) {

    }

    @Override
    public void updateArticle(Article article) {

    }

    @Override
    public void updateCategory(String ordinal, String newCatefory) {

    }
}
