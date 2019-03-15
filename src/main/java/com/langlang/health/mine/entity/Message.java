package com.langlang.health.mine.entity;

import java.io.Serializable;
import java.util.Date;

import com.langlang.health.system.entity.User;

import lombok.Data;

@Data
public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String message;
	private Date createTime;
	private Date updateTime;
	private Integer createId;
	private Integer updateId;
	private String userName;
	private String mobile;
}
