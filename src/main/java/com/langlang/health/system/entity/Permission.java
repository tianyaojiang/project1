package com.langlang.health.system.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by tyj on 2018/08/14.
 */

@Data
public class Permission {
    private Integer id;

    private String name;

    private Integer pid;

    private Integer zindex;

    private String istype;

    private String descpt;

    private String code;

    private String icon;

    private String page;

    private Date createTime;

    private Date updateTime;


}