package com.langlang.health.mobile.service;


import com.langlang.health.mobile.entity.HealthAppraiseData;

public interface HealthAppraiseService {
	HealthAppraiseData getAppraise(String uid, String time);

	boolean addAppraise(HealthAppraiseData appraise);

	boolean updateAppraise(HealthAppraiseData appraise);

	
}
