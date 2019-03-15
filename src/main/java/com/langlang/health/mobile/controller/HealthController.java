package com.langlang.health.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.mobile.entity.HealthAppraiseData;
import com.langlang.health.mobile.entity.HealthDangerfilterData;
import com.langlang.health.mobile.service.HealthAppraiseService;
import com.langlang.health.mobile.service.HealthDangerfilterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="筛查模块")
@RestController
@RequestMapping("/health")
public class HealthController {
	//危险评估模块
	@Autowired
	private HealthAppraiseService appraiseService;
	private HealthDangerfilterService dangerfilterService;
	
	@ApiOperation(value="展示危险评估", notes = "展示危险评估")
	@PostMapping(value = "/getAppraise")
	public RestResponseBo getAppraise(@RequestParam("uid") String uid, @RequestParam("time") String time) throws Exception{
		HealthAppraiseData healthAppraiseData = appraiseService.getAppraise(uid, time);
		return RestResponseBo.ok(healthAppraiseData);
	}
	
	@ApiOperation(value="新增危险评估", notes = "新增危险评估")
	@PostMapping(value = "/addAppraise")
	public RestResponseBo addAppraise(@RequestBody HealthAppraiseData appraise) throws Exception{
		boolean res = appraiseService.addAppraise(appraise);
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	
	@ApiOperation(value="更新危险评估", notes = "更新危险评估")
	@PostMapping(value = "/updateAppraise")
	public RestResponseBo updateAppraise(@RequestBody HealthAppraiseData appraise) throws Exception{
		boolean res = appraiseService.updateAppraise(appraise);
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	
	
	//慢病筛查模块
	@ApiOperation(value="展示慢病筛查", notes = "展示慢病筛查")
	@PostMapping(value = "/getDangerfilter")
	public RestResponseBo getDangerfilter(@RequestParam("uid") String appid, @RequestParam("time") String time) throws Exception{
		HealthDangerfilterData healthDangerfilterData = dangerfilterService.getDangerfilter(appid, time);
		return RestResponseBo.ok(healthDangerfilterData);
	}
	
	@ApiOperation(value="新增慢病筛查", notes = "新增慢病筛查")
	@PostMapping(value = "/addDangerfilter")
	public RestResponseBo addDangerfilter(@RequestBody HealthDangerfilterData dangerfilter) throws Exception{
		boolean res = dangerfilterService.addDangerfilter(dangerfilter);
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	
	@ApiOperation(value="更新慢病筛查", notes = "更新慢病筛查")
	@PostMapping(value = "/updateDangerfilter")
	public RestResponseBo updateDangerfilter(@RequestBody HealthDangerfilterData dangerfilter) throws Exception{
		boolean res = dangerfilterService.updateDangerfilter(dangerfilter);
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
}
