package com.langlang.health.mine.entity;

import java.util.List;
import lombok.Data;

@Data
public class DiseaseVo {
	private Integer id;
	private Integer userId;
	private String code;
	private List<DiseaseUtil> diseaseDetails;
	private Integer disType;
}
