package com.langlang.health.mine.entity;

import lombok.Data;

@Data
public class SumaryDisease {
	private boolean hasFamilyHis;
	private int familyHisId;
	private boolean hasDiseaseHis;
	private int diseaseHisId;
	private boolean hasAllergyHis;
	private int allergyHisId;
	private boolean hasOperationHis;
	private int operationHisId;
	private boolean hasTraumaHis;
	private int traumaHisId;
	private boolean hastransfusionHis;
	private int transfusionHisId;
}
