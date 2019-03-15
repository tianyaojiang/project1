package com.langlang.health.account.controller;

import com.langlang.health.common.entity.RestResponseBo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langlang.health.account.entity.BloodPressure;
import com.langlang.health.account.entity.BloodPressureDTO;
import com.langlang.health.account.service.BloodPressureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="血压")
@RestController
@RequestMapping("/bloodPressure")
public class BloodPressureController {

	@Autowired
	private BloodPressureService bloodPressureService;
	
	@ApiOperation(value="血压上传", notes = "")
	@PostMapping
    public RestResponseBo add(@RequestBody BloodPressureDTO bp) throws Exception{
		boolean res = bloodPressureService.saveBloodPressure(bp.getUserId(), bp.getTime(), bp.getUpper(), bp.getFloor(), bp.getHeart());
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	
	@ApiOperation(value="获取用户的血压数据")
	@GetMapping("/search/{userId}")
	public RestResponseBo getBloodPersures(@PathVariable("userId") Integer userId){
		List<BloodPressure> list = bloodPressureService.getBloodPressures(userId);
		return RestResponseBo.ok(list);
	}
	
	@ApiOperation(value="根据id获取血压数据")
	@GetMapping("/{id}")
	public RestResponseBo searchBloodPressure(@PathVariable("id") Integer bloodPressureId){
		BloodPressure bloodPressure = bloodPressureService.searchBloodPressure(bloodPressureId);
		return RestResponseBo.ok(bloodPressure);
	}
}
