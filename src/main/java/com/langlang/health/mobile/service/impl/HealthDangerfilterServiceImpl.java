package com.langlang.health.mobile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.langlang.health.mobile.entity.HealthDangerfilterData;
import com.langlang.health.mobile.mapper.HealthAppraiseMapper;
import com.langlang.health.mobile.mapper.HealthDangerfilterMapper;
import com.langlang.health.mobile.service.HealthDangerfilterService;

@Service
public class HealthDangerfilterServiceImpl implements HealthDangerfilterService {

	@Autowired
	private HealthDangerfilterMapper healthDangerfilterMapper;
	
	@Override
	public HealthDangerfilterData getDangerfilter(String appid, String time) {
		return healthDangerfilterMapper.getDangerfilter(appid,time);
	}

	@Override
	public boolean addDangerfilter(HealthDangerfilterData dangerfilter) {
		return healthDangerfilterMapper.addDangerfilter(dangerfilter);
	}

	@Override
	public boolean updateDangerfilter(HealthDangerfilterData dangerfilter) {
		return healthDangerfilterMapper.updateDangerfilter(dangerfilter);
	}

	
	


	

}
