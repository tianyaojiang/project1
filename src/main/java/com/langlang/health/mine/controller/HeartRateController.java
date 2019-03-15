package com.langlang.health.mine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.mine.entity.HeartRate;
import com.langlang.health.mine.service.HeartRateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="心率数据")
@RestController
@RequestMapping("/heartRate")
public class HeartRateController {
	@Autowired
	private HeartRateService heartRateService;
	
	@ApiOperation(value="获取用户的心率")
	@GetMapping("/search/{userId}")
	public RestResponseBo getHeartRate(@PathVariable("userId") Integer userId){
		
		HeartRate heartRate = heartRateService.searchHeartRate(userId);
		return new RestResponseBo<>().ok(heartRate);
	}
}
