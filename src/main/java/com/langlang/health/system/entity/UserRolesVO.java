package com.langlang.health.system.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by tyj on 2018/08/14.
 */
@Data
public class UserRolesVO {

	private Integer id;

	private String userId;

	private String roleId;

	private Date createTime;

	private Date updateTime;

}