package com.langlang.health.account.mapper;

import java.util.List;

import com.langlang.health.account.entity.BloodOxygen;

public interface BloodOxygenMapper {
	/**
	 * 保存血氧
	 * @param bloodPressure
	 * @return
	 */
	int saveBloodOxygen(BloodOxygen bloodOxygen);
	/**
	 * 查询最新的一条血氧数据
	 * @param userId
	 * @return
	 */
	BloodOxygen getBloodOxygenTop1(int userId);
	
	/**
	 * 获取用户的血氧数据
	 * @param userId
	 * @return
	 */
	List<BloodOxygen> getBloodOxygens(int userId);
	/**
	 * 根据id获取具体血氧数据
	 * @param oxId
	 * @return
	 */
	BloodOxygen getBloodOxygenById(int oxId);
}
