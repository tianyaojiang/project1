package com.langlang.health.account.service;

import java.util.List;

import com.langlang.health.account.entity.Equipment;

public interface EquipmentService {

	/**
	 * 添加设备
	 * @param userId
	 * @param type
	 * @param name
	 * @param mac
	 * @param uuid
	 * @param remark
	 * @return
	 */
	boolean saveEquipment(int userId, String type, String name, String mac, String uuid, String remark);
	/**
	 * 通过设备名称删除我的设备
	 * @param userId
	 * @param name
	 * @return
	 */
	boolean deleteEquipment(int id);
	/**
	 * 通过设备类型查询我的设备列表
	 * @param userId
	 * @param name
	 * @return
	 */
	List<Equipment> getEquipmentByType(int userId, String type);
	/**
	 * 通过设备名称查询我的设备列表（同一个用户下同一名称的设备只能存在一个）
	 * @param userId
	 * @param name
	 * @return
	 */
	Equipment getEquipmentByName(int userId, String name);
	/**
	 * 查询我的设备列表
	 * @param userId
	 * @return
	 */
	List<Equipment> getEquipmentList(int userId);
	
}
