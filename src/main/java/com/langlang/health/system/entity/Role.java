package com.langlang.health.system.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by tyj on 2018/08/13.
 */
@Data
public class Role {
    private Integer id;

    private String rolename;

    private String basePermissions;

    private String operationPermissions;

    private String code;

    private String descpt;

    private Integer createId;

    private Date createTime;

    private Date updateTime;


}