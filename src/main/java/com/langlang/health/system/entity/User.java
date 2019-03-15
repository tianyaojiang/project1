package com.langlang.health.system.entity;

import lombok.Data;

import java.util.*;

@Data
public class User  {

	private Integer id;

	private String username;

	private String realname;

	private Integer gender;

	private String mobile;

	private String email;

	private String password;

	private String department;

	private String rolename;

	private String icon;

	private String mcode;

	private Integer isDel;

	private Integer isLock;

	private Integer version;

	private Integer createId;

	private String createName;

	private Date createTime;

	private Integer updateId;

	private Date updateName;

	private Date updateTime;

	private Date sendTime;
	
	private Float weight;


	private List<Role> roleList = new ArrayList<>(); //用户所有角色值，在管理后台显示用户的角色
	private List<Role> roles = new ArrayList<>();     //用户所有角色值，用于shiro做角色权限的判断
	private List<Permission> perms = new ArrayList<>();     //用户所有权限值，用于shiro做资源权限的判断

}