package com.langlang.health.account.mapper;

import java.util.List;

import com.langlang.health.account.entity.BloodPressure;

/**
 * @ClassName 
 * @Description
 * @author DELL
 * @Date
 *
 */
/**
 * @ClassName 
 * @Description
 * @author DELL
 * @Date
 *
 */
public interface BloodPressureMapper {

	/**
	 * 保存血压
	 * @param bloodPressure
	 * @return
	 */
	int saveBloodPressure(BloodPressure bloodPressure);
	/**
	 * 查询最新的一条血压数据
	 * @param userId
	 * @return
	 */
	BloodPressure getBloodPressureTop1(int userId);
	
	
	/**
	 * 查询用户的血压数据
	 * @param userId
	 * @return
	 */
	List<BloodPressure> getBloodPressures(int userId);
	/**
	 * 根据id获取具体的血压
	 * @param bpId
	 * @return
	 */
	BloodPressure getBloodPressureById(int bpId);
}
