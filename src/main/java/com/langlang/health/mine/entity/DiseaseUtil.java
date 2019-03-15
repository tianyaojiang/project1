package com.langlang.health.mine.entity;

import java.util.Date;

import lombok.Data;

@Data
public class DiseaseUtil {
	private String disease;
	private String patient;
	private Date dianosticTime;
}
