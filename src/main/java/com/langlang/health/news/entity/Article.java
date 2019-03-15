package com.langlang.health.news.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by tyj on 2019/01/10.
 */

@Data
public class Article {
    /**
     * 主键id
     */
    private Integer cid;

    /**
     * 内容标题
     */
    private String title;

    /**
     * 内容缩略名
     */
    private String slug;


    /**
     * 内容所属用户id
     */
    private Integer authorId;


    /**
     * 内容所属用户昵称
     */
    private String author;

    /**
     * 内容类别
     */
    private String type;

    /**
     * 内容状态
     */
    private String status;

    /**
     * 标签列表
     */
    private String tags;

    /**
     * 分类列表
     */
    private String categories;

    /**
     * 点击次数
     */
    private Integer hits;

    /**
     * 内容所属评论数
     */
    private Integer commentsNum;

    /**
     * 是否允许评论
     */
    private Boolean allowComment;

    /**
     * 是否允许ping
     */
    private Boolean allowPing;

    /**
     * 允许出现在聚合中
     */
    private Boolean allowFeed;

    /**
     * 内容文字
     */
    private String content;

    /**
     * 是否置顶：0不置顶 1置顶
     */
    private Integer stick;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 版本号
     */
    private String version;

    /**
    *  文章下的评论
    */

    List<Comment> comments;

}
