package com.langlang.health.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.mobile.entity.HealthAppraiseData;
import com.langlang.health.mobile.mapper.HealthAppraiseMapper;
import com.langlang.health.mobile.service.HealthAppraiseService;

@Service
public class HealthAppraiseServiceImpl implements HealthAppraiseService {

	@Autowired
	private HealthAppraiseMapper healthAppraiseMapper;
	
	@Override
	public HealthAppraiseData getAppraise(String uid, String time) {
		HealthAppraiseData appraise = new HealthAppraiseData();
		appraise.setUid(uid);
		appraise.setTime(time);
		return healthAppraiseMapper.getAppraise(appraise);
	}

	@Override
	public boolean addAppraise(HealthAppraiseData appraise) {
		return healthAppraiseMapper.addAppraise(appraise);
	}

	@Override
	public boolean updateAppraise(HealthAppraiseData appraise) {
		return healthAppraiseMapper.updateAppraise(appraise);
	}



	

}
