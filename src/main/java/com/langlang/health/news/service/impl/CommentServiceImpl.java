package com.langlang.health.news.service.impl;

import com.github.pagehelper.PageInfo;
import com.langlang.health.common.exception.HealthException;
import com.langlang.health.news.entity.Article;
import com.langlang.health.news.entity.Banner;
import com.langlang.health.news.entity.Comment;
import com.langlang.health.news.mapper.ArticleMapper;
import com.langlang.health.news.mapper.BannerMapper;
import com.langlang.health.news.mapper.CommentMapper;
import com.langlang.health.news.service.BannerService;
import com.langlang.health.news.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by tyj on 2018/08/15.
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public void insertComment(Comment comment) {
        if (null == comment) {
            throw new HealthException("评论对象为空");
        }
        if (StringUtils.isBlank(comment.getAuthor())) {
            comment.setAuthor("热心网友");
        }
        if (!StringUtils.isNotBlank(comment.getMail()) ) {
            throw new HealthException("请输入正确的邮箱格式");
        }
        if (StringUtils.isBlank(comment.getContent())) {
            throw new HealthException("评论内容不能为空");
        }
        if (comment.getContent().length() < 5 || comment.getContent().length() > 2000) {
            throw new HealthException("评论字数在5-2000个字符");
        }
        if (null == comment.getCid()) {
            throw new HealthException("评论文章不能为空");
        }
        Article article = articleMapper.getArticlesById(comment.getCid());
        if (null == article) {
            throw new HealthException("不存在的文章");
        }
        comment.setAuthorId(article.getAuthorId());
        comment.setCreateTime(new Date());
        commentMapper.insertSelective(comment);

    }

    @Override
    public PageInfo<Comment> getComments(Integer cid, int page, int limit) {
        return null;
    }

    @Override
    public PageInfo<Comment> getCommentsWithPage(Comment comment, int page, int limit) {
        return null;
    }

    @Override
    public List<Comment> getCommentsByArticleId(Integer articleId) {
        return commentMapper.getCommentsByArticleId(articleId);
    }

    @Override
    public List<Comment> getCommentsByUserId(Integer userId) {
        return commentMapper.getCommentsByUserId(userId);
    }

    @Override
    public Comment getCommentById(Integer coid) {
        return null;
    }

    @Override
    public void delete(Integer coid, Integer cid) {

    }

    @Override
    public void update(Comment comments) {

    }
}
