package com.langlang.health.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by tyj on 2018/11/27.
 */

@Data
public class LogVo implements Serializable {
    /**
     * 日志主键
     */
    private Integer id;

    /**
     * 产生的动作
     */
    private String action;

    /**
     * 产生的数据
     */
    private String data;

    /**
     * 发生人id
     */
    private Integer userId;


    private Integer roleId;

    /**
     * 日志产生的ip
     */
    private String ip;

    /**
     * 日志创建时间
     */
    private Date createTime;


    private Date updateTime;

}
