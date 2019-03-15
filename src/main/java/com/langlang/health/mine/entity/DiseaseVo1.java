package com.langlang.health.mine.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DiseaseVo1 {
	private Integer userId;
	private String code;
	private String value;
	private Date createTime;
	private String patients;
	private Integer disType;
	private Date dianosticTime;
}
