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

import com.langlang.health.account.entity.BloodSugar;
import com.langlang.health.account.entity.BloodSugarDTO;
import com.langlang.health.account.service.BloodSugarService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="血糖")
@RestController
@RequestMapping("/bloodSugar")
public class BloodSugarController {

	@Autowired
	private BloodSugarService bloodSugarService;
	
	@ApiOperation(value="血糖上传", notes = "")
	@PostMapping
    public RestResponseBo add(@RequestBody BloodSugarDTO bs) throws Exception{
		boolean res = bloodSugarService.saveBloodSugar(bs.getUserId(), bs.getTime(), bs.getName(), bs.getType(), bs.getValue(), bs.getResult());
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	@ApiOperation(value="获取用户血糖", notes="")
	@GetMapping("search/{userId}")
	public RestResponseBo search(@PathVariable("userId") Integer userId){
		List<BloodSugar> list = bloodSugarService.getBloodSugars(userId);
		return RestResponseBo.ok(list);
	}
	@ApiOperation(value="获取具体血糖")
	@GetMapping("/{id}")
	public RestResponseBo searchBloodSugar(@PathVariable("id") Integer bsId){
		BloodSugar bloodSugar = bloodSugarService.searchBloodSugarById(bsId);
		return RestResponseBo.ok(bloodSugar);
	}
	
}
