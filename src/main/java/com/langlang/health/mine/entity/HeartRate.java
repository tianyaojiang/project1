package com.langlang.health.mine.entity;

import java.util.Date;

import lombok.Data;

@Data
public class HeartRate {
	private String userName;
	private String fileName;
	private Integer fileType;
	private String fileTime;
	private Date fileDate;
	private String heartRate;
	private String maxrr;
	private Integer beatNum;
	private String pnn50;
	private Integer respN;
	private Integer respType;
	private String hf;
	private String lf;
	private String vlf;
	private Integer basicRR;
	private String qrsTime;
	private String prInterval;
	private String qtInterval;
	private String qtc;
	private String pampLitude;
	private String qampLitude;
	private String rampLitude;
	private String sampLitude;
	private String tampLitude;
	private String tpe;
	private Integer maxxl;
	private Integer minxl;
	private Integer avgxl;
	private String upLimit;
	private String downLimit;
	private Date createTime;
}
