package com.langlang.health.account.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.account.entity.Equipment;
import com.langlang.health.account.mapper.EquipmentMapper;
import com.langlang.health.account.service.EquipmentService;

@Service
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	private EquipmentMapper equipmentMapper;
	
	@Override
	public boolean saveEquipment(int userId, String type, String name, String mac, String uuid, String remark) {
		Equipment equipment = new Equipment();
		equipment.setUserId(userId);
		equipment.setType(type);
		equipment.setName(name);
		equipment.setMac(mac);
		equipment.setMac(mac);
		equipment.setRemark(remark);
		equipment.setCreateTime(new Date());
		equipment.setUpdateTime(new Date());
		int res = equipmentMapper.saveEquipment(equipment);
		return res > 0;
	}

	@Override
	public boolean deleteEquipment(int id) {
		int res = equipmentMapper.deleteEquipment(id);
		return res > 0;
	}

	@Override
	public List<Equipment> getEquipmentByType(int userId, String type) {
		Equipment equipment = new Equipment();
		equipment.setUserId(userId);
		equipment.setType(type);
		return equipmentMapper.getEquipment(equipment);
	}

	@Override
	public Equipment getEquipmentByName(int userId, String name) {
		Equipment equipment = new Equipment();
		equipment.setUserId(userId);
		equipment.setName(name);
		List<Equipment> list = equipmentMapper.getEquipment(equipment);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<Equipment> getEquipmentList(int userId) {
		Equipment equipment = new Equipment();
		equipment.setUserId(userId);
		return equipmentMapper.getEquipment(equipment);
	}

}
