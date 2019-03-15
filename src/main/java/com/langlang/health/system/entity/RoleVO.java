package com.langlang.health.system.entity;


import lombok.Data;

import java.util.List;

/**
 * Created by tyj on 2018/08/14.
 */
@Data
public class RoleVO {

	private Integer id;

	private String roleName;

	private String descpt;

	private String code;

	private Integer insertUid;

	private String insertTime;
	//角色下的权限ids
	private List<RolePermissionKey> rolePerms;


}
