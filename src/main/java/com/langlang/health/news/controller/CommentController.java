package com.langlang.health.news.controller;

import com.github.pagehelper.PageInfo;
import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.common.exception.HealthException;
import com.langlang.health.news.entity.Article;
import com.langlang.health.news.entity.Comment;
import com.langlang.health.news.service.ArticleService;
import com.langlang.health.news.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by tyj on 2019/01/10.
 */
@Api(tags = "健康资讯-评论")
@RestController
@RequestMapping("/news")
@Slf4j
public class CommentController {

    @Autowired
    ArticleService articleService;
    @Autowired
    CommentService commentService;


    /**
     * 获取文章详细页评论
     * @param request 请求
     * @param cid  文章主键
     * @return
     */
    @RequestMapping(value = "getArticleComments/{cid}",method = RequestMethod.POST)
    public RestResponseBo getArticleComments(HttpServletRequest request, @PathVariable String cid) {
        Article article = articleService.getArticlesById(Integer.parseInt( cid));
        if (article == null){
            String message = "文章不存在或id有误！";
            return RestResponseBo.fail(message);
        }
        //获取评论列表
        List<Comment> comments = commentService.getCommentsByArticleId(article.getCid());
        return RestResponseBo.ok(comments,1);
    }


    @RequestMapping(value = "comment/{p}",method = RequestMethod.POST)
    @ResponseBody
    public RestResponseBo getComments(HttpServletRequest request, @PathVariable int p, @RequestParam(value = "limit", defaultValue = "12") int limit, @PathVariable String cid) {
        p = p < 0 || p > 100 ? 1 : p;
        PageInfo<Article> articles = articleService.getArticles(p, limit);
        return RestResponseBo.ok(articles, 1);
    }


    /**
     * 评论操作
     */
    @RequestMapping(value = "comment",method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "cid", value = "用户ID", required = true, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "coid", value = "页码", required = false, paramType = "query", dataType = "int"),
//            @ApiImplicitParam(name = "author", value = "作者", required = false, paramType = "query", dataType = "String")
//    })
    @ResponseBody
    @Transactional(rollbackFor = HealthException.class)
    public RestResponseBo comment(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam Integer cid, @RequestParam Integer coid,
                                  @RequestParam String author, @RequestParam String mail,
                                  @RequestParam String url, @RequestParam String text) {


        if (null == cid || StringUtils.isBlank(text)) {
            return RestResponseBo.fail("请输入完整后评论");
        }

        if (StringUtils.isNotBlank(author) && author.length() > 50) {
            return RestResponseBo.fail("姓名过长");
        }

        if (text.length() > 200) {
            return RestResponseBo.fail("请输入200个字符以内的评论");
        }

        Comment comments = new Comment();
        comments.setAuthor(author);
        comments.setCid(cid);
        comments.setIp(request.getRemoteAddr());
        comments.setUrl(url);
        comments.setContent(text);
        comments.setMail(mail);
        comments.setParent(coid);
        try {
            commentService.insertComment(comments);
            cookie("tale_remember_author", URLEncoder.encode(author, "UTF-8"), 7 * 24 * 60 * 60, response);
            cookie("tale_remember_mail", URLEncoder.encode(mail, "UTF-8"), 7 * 24 * 60 * 60, response);
            if (StringUtils.isNotBlank(url)) {
                cookie("tale_remember_url", URLEncoder.encode(url, "UTF-8"), 7 * 24 * 60 * 60, response);
            }
            return RestResponseBo.ok(1 );
        } catch (Exception e) {
            String msg = "评论发布失败";
            if (e instanceof HealthException) {
                msg = e.getMessage();
            } else {
                log.error(msg, e);
            }
            return RestResponseBo.fail(msg);
        }
    }


    /**
     * 设置cookie
     *
     * @param name
     * @param value
     * @param maxAge
     * @param response
     */
    private void cookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }
}
