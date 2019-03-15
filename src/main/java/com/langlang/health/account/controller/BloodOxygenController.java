package com.langlang.health.account.controller;

import com.langlang.health.common.entity.RestResponseBo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langlang.health.account.entity.BloodOxygen;
import com.langlang.health.account.entity.BloodOxygenDTO;
import com.langlang.health.account.service.BloodOxygenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="血氧")
@RestController
@RequestMapping("/bloodOxygen")
public class BloodOxygenController {

	@Autowired
	private BloodOxygenService bloodOxygenService;
	
	@ApiOperation(value="血氧上传", notes = "")
	@PostMapping
    public RestResponseBo add(@RequestBody BloodOxygenDTO spo) throws Exception{
		boolean res = bloodOxygenService.saveBloodOxygen(spo.getUserId(), spo.getTime(), spo.getValue(), spo.getHeart());
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	
	@ApiOperation(value="获取血氧数据", notes="")
	@GetMapping("searchAll/{userId}")
	public RestResponseBo searchBloodOxygens(@PathVariable("userId") Integer userId){
		List<BloodOxygen> list = bloodOxygenService.getBloodOxygens(userId);
		return RestResponseBo.ok(list);
	}
	
	@ApiOperation(value="获取具体血氧数据")
	@GetMapping("/{id}")
	public RestResponseBo searchBloodOxygenById(@PathVariable("id") Integer boId){
		BloodOxygen bloodOxygen = bloodOxygenService.searchBloodOxygen(boId);
		return RestResponseBo.ok(bloodOxygen);
	}
}
