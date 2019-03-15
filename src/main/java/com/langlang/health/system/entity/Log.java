package com.langlang.health.system.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Log {
    private Short id;

    private Integer userId;

    private Integer roleId;

    private Date updateTime;

    private Date createTime;


}