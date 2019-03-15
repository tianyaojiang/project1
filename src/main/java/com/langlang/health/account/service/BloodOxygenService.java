package com.langlang.health.account.service;

import java.util.Date;
import java.util.List;

import com.langlang.health.account.entity.BloodOxygen;

public interface BloodOxygenService {
	/**
	 * 保存血氧
	 * @param userId
	 * @param time
	 * @param value
	 * @param heart
	 * @return
	 */
	boolean saveBloodOxygen(int userId, Date time, int value, int heart);
	/**
	 * 查询最近的一条血氧数据
	 * @param userId
	 * @return
	 */
	BloodOxygen getBloodOxygenTop1(int userId);
	
	/**获取用户的血氧数据
	 * @param userId
	 * @return
	 */
	List<BloodOxygen> getBloodOxygens(int userId);
	
	/**
	 * 获取具体的血氧
	 * @param id
	 * @return
	 */
	BloodOxygen searchBloodOxygen(int id);
}
