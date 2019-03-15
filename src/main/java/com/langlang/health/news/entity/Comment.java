package com.langlang.health.news.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by tyj on 2019/01/10.
 */

@Data
public class Comment {
    /**
     * comment表主键
     */
    private Integer coid;

    /**
     * article表主键,关联字段
     */
    private Integer cid;

    /**
     * article标题
     */
    private String postTitle;

    /**
     * 评论作者
     */
    private String author;

    /**
     * 评论所属用户id
     */
    private Integer authorId;

    /**
     * 评论所属内容作者id
     */
    private Integer ownerId;

    /**
     * 评论者邮件
     */
    private String mail;

    /**
     * 评论者网址
     */
    private String url;

    /**
     * 评论者ip地址
     */
    private String ip;

    /**
     * 评论者客户端
     */
    private String agent;

    /**
     * 评论类型
     */
    private String type;

    /**
     * 评论状态
     */
    private String status;

    /**
     * 父级评论
     */
    private Integer parent;

    /**
     * 评论内容
     */
    private String content;


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
}
