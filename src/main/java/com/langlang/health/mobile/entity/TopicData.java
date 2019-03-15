package com.langlang.health.mobile.entity;

import java.util.Date;

import lombok.Data;

/**
* created by xb on Jan 28, 2019
*/
@Data
public class TopicData {

	private Integer userId;
	private String messName;
	private Integer messValue;
	private String messType;
	private Date createTime;
}
