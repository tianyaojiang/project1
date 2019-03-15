package com.langlang.health.account.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.account.entity.BloodOxygen;
import com.langlang.health.account.mapper.BloodOxygenMapper;
import com.langlang.health.account.service.BloodOxygenService;

@Service
public class BloodOxygenServiceImpl implements BloodOxygenService {

	@Autowired
	private BloodOxygenMapper bloodOxygenMapper;

	@Override
	public boolean saveBloodOxygen(int userId, Date time, int value, int heart) {
		BloodOxygen bloodOxygen = new BloodOxygen();
		bloodOxygen.setUserId(userId);
		bloodOxygen.setDate(time);
		bloodOxygen.setTime(time);
		bloodOxygen.setValue(value);
		bloodOxygen.setHeart(heart);
		bloodOxygen.setCreateTime(new Date());
		int res = bloodOxygenMapper.saveBloodOxygen(bloodOxygen);
		return res > 0;
	}

	@Override
	public BloodOxygen getBloodOxygenTop1(int userId) {
		return bloodOxygenMapper.getBloodOxygenTop1(userId);
	}

	@Override
	public List<BloodOxygen> getBloodOxygens(int userId) {
		return bloodOxygenMapper.getBloodOxygens(userId);
	}

	@Override
	public BloodOxygen searchBloodOxygen(int id) {
		// TODO Auto-generated method stub
		return bloodOxygenMapper.getBloodOxygenById(id);
	}

}
