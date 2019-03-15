package com.langlang.health.news.service;

import com.github.pagehelper.PageInfo;
import com.langlang.health.news.entity.Article;
import com.langlang.health.news.entity.Banner;

import java.util.List;

/**
 * Created by tyj on 2018/1/10
 */
public interface ArticleService {
    
    /**
     *查询文章返回多条数据
     * @param p 当前页
     * @param limit 每页条数
     * @return ArticleVo
     */
    PageInfo<Article> getArticles(Integer p, Integer limit);


    /**
     * 根据id或slug获取文章
     *
     * @param id id
     * @return ArticleVo
     */
    Article getArticlesById(Integer id);

    /**
     * 根据id更新
     * @param article
     */
    void updateArticleByCid(Article article);


    /**
     * 查询分类/标签下的文章归档
     * @param mid mid
     * @param page page
     * @param limit limit
     * @return ArticleVo
     */
    PageInfo<Article> getArticles(Integer mid, int page, int limit);

    /**
     * 搜索、分页
     * @param keyword keyword
     * @return ArticleVo
     */
    PageInfo<Article> getArticles(String keyword);


    /**
     * @param article
     * @param page
     * @param limit
     * @return
     */
    PageInfo<Article> getArticlesWithpage(Article article, Integer page, Integer limit);
    /**
     * 根据文章id删除
     * @param cid
     */
    void deleteByCid(Integer cid);

    /**
     * 编辑文章
     * @param article
     */
    void updateArticle(Article article);


    /**
     * 更新原有文章的category
     * @param ordinal
     * @param newCatefory
     */
    void updateCategory(String ordinal,String newCatefory);

}
