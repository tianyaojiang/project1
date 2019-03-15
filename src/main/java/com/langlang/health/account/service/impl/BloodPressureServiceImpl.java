package com.langlang.health.account.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.account.entity.BloodPressure;
import com.langlang.health.account.mapper.BloodPressureMapper;
import com.langlang.health.account.service.BloodPressureService;

@Service
public class BloodPressureServiceImpl implements BloodPressureService {

	@Autowired
	private BloodPressureMapper bloodPressureMapper;
	
	@Override
	public boolean saveBloodPressure(int userId, Date time, int upper, int floor, int heart) {
		BloodPressure bloodPressure = new BloodPressure();
		bloodPressure.setUserId(userId);
		bloodPressure.setDate(time);
		bloodPressure.setTime(time);
		bloodPressure.setUpper(upper);
		bloodPressure.setFloor(floor);
		bloodPressure.setHeart(heart);
		bloodPressure.setCreateTime(new Date());
		int res = bloodPressureMapper.saveBloodPressure(bloodPressure);
		return res > 0;
	}

	@Override
	public BloodPressure getBloodPressureTop1(int userId) {
		return bloodPressureMapper.getBloodPressureTop1(userId);
	}

	@Override
	public List<BloodPressure> getBloodPressures(int userId) {
		// TODO Auto-generated method stub
		return bloodPressureMapper.getBloodPressures(userId);
	}

	@Override
	public BloodPressure searchBloodPressure(int bpId) {
		// TODO Auto-generated method stub
		return bloodPressureMapper.getBloodPressureById(bpId);
	}
}
