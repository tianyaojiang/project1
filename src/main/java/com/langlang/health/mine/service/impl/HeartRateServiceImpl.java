package com.langlang.health.mine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.langlang.health.mine.entity.HeartRate;
import com.langlang.health.mine.mapper.HeartRateMapper;
import com.langlang.health.mine.service.HeartRateService;

@Service("heartRateService")
public class HeartRateServiceImpl implements HeartRateService{
	
	@Autowired
	private HeartRateMapper heartRateMpper;
	
	@Override
	public HeartRate searchHeartRate(Integer userId) {
		// TODO Auto-generated method stub
		return heartRateMpper.searchHeartRateByUser(userId, "admin", 1);
	}

}
