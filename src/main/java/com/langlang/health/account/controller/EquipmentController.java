package com.langlang.health.account.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langlang.health.account.entity.Equipment;
import com.langlang.health.account.entity.EquipmentDTO;
import com.langlang.health.account.service.EquipmentService;
import com.langlang.health.common.entity.RestResponseBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags="我的设备")
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private EquipmentService equipmentService;
	
	@ApiOperation(value="我的设备列表", notes = "通过用户ID查询用户的设备列表")
	@GetMapping
    public RestResponseBo<?> get(@RequestParam("userId") int userId) throws Exception{
		List<Equipment> list = equipmentService.getEquipmentList(userId);
		return RestResponseBo.ok(list);
	}
	
	@ApiOperation(value="新增设备", notes = "")
	@ApiImplicitParams({
	    @ApiImplicitParam(name="equipment", value="设备对象JSON格式", required=true, dataType="EquipmentDTO")
	})
	@PostMapping
    public RestResponseBo<?> add(@RequestBody EquipmentDTO equipment) throws Exception{
		Equipment temp = equipmentService.getEquipmentByName(equipment.getUserId(), equipment.getName());
		if(temp!=null){
			return RestResponseBo.fail(messageSource.getMessage("message.equipment_already_exists", null, LocaleContextHolder.getLocale()));
		}else{
			boolean res = equipmentService.saveEquipment(equipment.getUserId(), equipment.getType(), equipment.getName(), equipment.getMac(), equipment.getUuid(), equipment.getRemark());
			if(res){
				return RestResponseBo.ok();
			}
			return RestResponseBo.fail();
		}
	}
	
	@ApiOperation(value="删除设备", notes = "通过设备ID删除设备")
	@DeleteMapping
    public RestResponseBo<?> delete(@RequestParam("id") int id) throws Exception{
		boolean res = equipmentService.deleteEquipment(id);
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	
}
