package com.langlang.health.matlab.entity;

import java.util.Date;

import lombok.Data;

@Data
public class MatlabLog {

	private String userName;
	private String fileName;
	private Integer fileType;
	private String fileTime;
	private Date date;
	private Date time;
	private Integer year;
	private Integer month;
	private Integer day;
	private Integer status;
	private Integer number;
	private Date createTime;
	private Date updateTime;
}
