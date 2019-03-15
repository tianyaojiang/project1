package com.langlang.health.account.mapper;

import java.util.List;

import com.langlang.health.account.entity.BloodSugar;

public interface BloodSugarMapper {

	/**
	 * 保存血糖
	 * @param bloodSugar
	 * @return
	 */
	int saveBloodSugar(BloodSugar bloodSugar);
	/**
	 * 查询最新的一条血糖数据
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
	 * 根据id获取具体血糖数据
	 * @param bsId
	 * @return
	 */
	BloodSugar getBloodSugarById(int bsId);
}
