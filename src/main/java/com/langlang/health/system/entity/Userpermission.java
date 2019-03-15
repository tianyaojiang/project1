package com.langlang.health.system.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Userpermission {
    private Short id;

    private Integer userId;

    private Integer permId;

    private Date createTime;

    private Date updateTime;


}