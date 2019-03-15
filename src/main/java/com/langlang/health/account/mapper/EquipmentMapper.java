package com.langlang.health.account.mapper;

import java.util.List;

import com.langlang.health.account.entity.Equipment;

public interface EquipmentMapper {

	/**
	 * 查询我的设备列表
	 * @param equipment
	 * @return
	 */
	List<Equipment> getEquipment(Equipment equipment);
	/**
	 * 添加我的设备
	 * @param equipment
	 * @return
	 */
	int saveEquipment(Equipment equipment);
	/**
	 * 通过ID删除我的设备
	 * @param id
	 * @return
	 */
	int deleteEquipment(int id);
	
}
