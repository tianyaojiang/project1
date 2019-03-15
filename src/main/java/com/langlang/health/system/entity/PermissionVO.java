package com.langlang.health.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by tyj on 2018/08/14.
 */
@Data
public class PermissionVO implements Serializable{

	private String id;

	private String name;

	private String pId;

	private String istype;

	private String code;

	private String page;

	private String icon;

	private String zindex;

	private boolean checked;

	private boolean open;


}