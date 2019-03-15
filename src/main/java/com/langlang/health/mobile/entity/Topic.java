package com.langlang.health.mobile.entity;
import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;


/**
 * 话题
 * @author xb
 *
 */
@Data
public class Topic {
	public Topic(Integer userId, String topic, String color, int value, int isShow, Date date, Date date2) {
		this.userId = userId;
		this.topic = topic;
		this.color = color;
		this.value = value;
		this.isShow = isShow;
		this.createTime = date;
		this.updateTime = date2;
	}
	public Topic(){
		
	}
	private Integer userId;
	private String topic;
	private String color;
	private Integer value;
	private Integer isShow;
	private Date createTime;
	private Date updateTime;
}
