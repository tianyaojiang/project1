package com.langlang.health.mine.entity;

import java.util.Date;
import java.util.List;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class Disease {
	private Integer id;
	private Integer userId;
	private String code;
	private String value;
	private Date updateTime;
	private String patients;
	private Integer disType;
	private Date dianosticTime;
	private Integer pregnanTimes;
	private Integer productTimes;
	private Integer heightChildHeight;
}
