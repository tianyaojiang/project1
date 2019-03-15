package com.langlang.health.account.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.account.entity.BloodSugar;
import com.langlang.health.account.mapper.BloodSugarMapper;
import com.langlang.health.account.service.BloodSugarService;

@Service
public class BloodSugarServiceImpl implements BloodSugarService {

	@Autowired
	private BloodSugarMapper bloodSugarMapper;

	@Override
	public boolean saveBloodSugar(int userId, Date time, String name, String type, String value, String result) {
		BloodSugar bloodSugar = new BloodSugar();
		bloodSugar.setUserId(userId);
		bloodSugar.setDate(time);
		bloodSugar.setTime(time);
		bloodSugar.setName(name);
		bloodSugar.setType(type);
		bloodSugar.setValue(value);
		bloodSugar.setResult(result);
		bloodSugar.setCreateTime(new Date());
		int res = bloodSugarMapper.saveBloodSugar(bloodSugar);
		return res > 0;
	}

	@Override
	public BloodSugar getBloodSugarTop1(int userId) {
		return bloodSugarMapper.getBloodSugarTop1(userId);
	}

	@Override
	public List<BloodSugar> getBloodSugars(int userId) {
		return bloodSugarMapper.getBloodSugars(userId);
	}

	@Override
	public BloodSugar searchBloodSugarById(int bsId) {
		// TODO Auto-generated method stub
		return bloodSugarMapper.getBloodSugarById(bsId);
	}

}
