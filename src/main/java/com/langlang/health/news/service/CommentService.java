package com.langlang.health.news.service;

import com.github.pagehelper.PageInfo;
import com.langlang.health.news.entity.Banner;
import com.langlang.health.news.entity.Comment;

import java.util.List;

/**
 * Created by tyj on 2018/08/15.
 */
public interface CommentService {

    /**
     * 保存对象
     * @param comment
     */
    void insertComment(Comment comment);

    /**
     * 获取文章下的评论
     * @param cid
     * @param page
     * @param limit
     * @return CommentBo
     */
    PageInfo<Comment> getComments(Integer cid, int page, int limit);

    /**
     * 获取文章下的评论
     * @param comment
     * @param page
     * @param limit
     * @return CommentVo
     */
    PageInfo<Comment> getCommentsWithPage(Comment comment, int page, int limit);


    /**
     * 根据文章id查询评论
     * @param articleId
     * @return
     */
    List<Comment> getCommentsByArticleId(Integer articleId);


    /**
     * 根据文章id查询评论
     * @param userId
     * @return
     */
    List<Comment> getCommentsByUserId(Integer userId);


    /**
     * 根据主键查询评论
     * @param coid
     * @return
     */
    Comment getCommentById(Integer coid);


    /**
     * 删除评论
     * @param coid
     * @param cid
     * @throws Exception
     */
    void delete(Integer coid, Integer cid);

    /**
     * 更新评论状态
     * @param comments
     */
    void update(Comment comments);

}
