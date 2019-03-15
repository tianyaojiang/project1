package com.langlang.health.news.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by tyj on 2019/01/10.
 */

@Data
public class Banner {

    /**
     * 广告id
     */
    private Integer id;

    /**
     * 广告标题
     */
    private String title;

    /**
     * 广告url
     */
    private String url;

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
}
