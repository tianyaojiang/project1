package com.langlang.health.account.service;

import java.util.Date;
import java.util.List;

import com.langlang.health.account.entity.BloodSugar;

public interface BloodSugarService {
	/**
	 * 保存血糖
	 * @param userId
	 * @param time
	 * @param name
	 * @param type
	 * @param value
	 * @param result
	 * @return
	 */
	boolean saveBloodSugar(int userId, Date time, String name, String type, String value, String result);
	/**
	 * 查询最近的一条血糖数据
	 * @param userId
	 * @return
	 */
	BloodSugar getBloodSugarTop1(int userId);
	/**
	 * 获取用户的血糖数据
	 * @param userId
	 * @return
	 */
	List<BloodSugar> getBloodSugars(int userId);
	/**
	 * 获取具体的血糖
	 * @param bsId
	 * @return
	 */
	BloodSugar searchBloodSugarById(int bsId);
}
