package com.langlang.health.news.controller;

import com.github.pagehelper.PageInfo;
import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.news.entity.Article;
import com.langlang.health.news.entity.Banner;
import com.langlang.health.news.entity.Comment;
import com.langlang.health.news.service.ArticleService;
import com.langlang.health.news.service.BannerService;
import com.langlang.health.news.service.CommentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyj on 2019/01/10.
 */

@Api(tags="健康资讯-资讯")
@RestController
@RequestMapping("/news")
@Slf4j
public class ArticleController {

    @Autowired
    BannerService bannerService;
    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;


    /**
     * 获取banner
     * @param request request
     * @return 主页
     */
    @RequestMapping(value = "getBanners" ,method = RequestMethod.GET)
    public RestResponseBo getBanners(HttpServletRequest request) {
        List<Banner> banners = bannerService.getBannerList();
        return RestResponseBo.ok(banners,1);
    }
    /**
     * 获取getSliders
     * @param request request
     * @return 主页
     */

    @RequestMapping(value = "getSliders" ,method = RequestMethod.GET)
    public RestResponseBo getSliders(HttpServletRequest request) {
        List slider =new ArrayList();
        slider.add("推荐");
        slider.add("医疗");
        slider.add("科技");
        slider.add("体育");
        return RestResponseBo.ok(slider,1);
    }

    /**
     * 文章列表
     * @param request request
     * @param p       第几页
     * @param limit   每页大小
     * @return 主页
     */
    @RequestMapping(value = "getArticleList/{p}",method = RequestMethod.GET)
    public RestResponseBo getArticleList(HttpServletRequest request, @PathVariable int p, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        p = p < 0 || p > 100 ? 1 : p;
        PageInfo<Article> articles = articleService.getArticles(p, limit);
        return RestResponseBo.ok(articles,1);
    }

    /**
     * 文章详细页
     * @param request 请求
     * @param cid  文章主键
     * @return
     */
    @RequestMapping(value = "article/{cid}",method = RequestMethod.POST)
    public RestResponseBo getArticle(HttpServletRequest request, @PathVariable String cid) {
        Article article = articleService.getArticlesById(Integer.parseInt( cid));
        //获取评论列表
        List<Comment> comments = commentService.getCommentsByArticleId(article.getCid());
        article.setComments(comments);
        return RestResponseBo.ok(article,1);
    }






}
