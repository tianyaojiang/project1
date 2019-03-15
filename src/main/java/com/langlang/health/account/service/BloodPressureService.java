package com.langlang.health.account.service;

import java.util.Date;
import java.util.List;

import com.langlang.health.account.entity.BloodPressure;

public interface BloodPressureService {

	/**
	 * 保存血压
	 * @param userId
	 * @param upper
	 * @param floor
	 * @param heart
	 * @return
	 */
	boolean saveBloodPressure(int userId, Date time, int upper, int floor, int heart);
	/**
	 * 查询最近的一条血压数据
	 * @param userId
	 * @return
	 */
	BloodPressure getBloodPressureTop1(int userId);
	
	/**获取用户的血压数据
	 * @param userId
	 * @return
	 */
	List<BloodPressure> getBloodPressures(int userId);
	/**获取具体的血压值
	 * @param bpId
	 * @return
	 */
	BloodPressure searchBloodPressure(int bpId);
}
