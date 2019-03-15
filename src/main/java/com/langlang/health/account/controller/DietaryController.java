
package com.langlang.health.account.controller;

import java.awt.HeadlessException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import com.langlang.health.account.service.DietaryService;
import com.langlang.health.common.entity.RestResponseBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;


/**
* @author htd
* @version 创建时间：2019年1月8日 下午4:09:53
*/
@Api(tags="膳食信息获取")
@SuppressWarnings("all")
@Controller
@RequestMapping("/getDietary")
@Slf4j
public class DietaryController {

	
	@Autowired
	private DietaryService dietaryService;
	private Map<String,String> map = new HashMap<>();
	@PostMapping("/getDietaryPlan")
	@ApiOperation(value="膳食计划获取")
	@ResponseBody
	public RestResponseBo getDietaryPlan(){
		try {
			List<Map> dietaryPlan = dietaryService.getDietaryPlan();
			RestResponseBo.ok(dietaryPlan);
		} catch (Exception e) {
			String message = "获取膳食计划信息失败";
			if (e instanceof HeadlessException) {
				message = e.getMessage();
			}else{
				log.error(message, e);
			}
			return RestResponseBo.fail(message);
		}
		return RestResponseBo.ok();
	}
	@PostMapping("/getDietaryHistory")
	@ResponseBody
	@ApiOperation(value="膳食历史记录查询")
	public List<Map> getDietaryHistory(){
		List<Map> dietaryHistory = null;
		try {
			dietaryHistory = dietaryService.getDietaryHistory();
		} catch (Exception e) {
			String message = "获取膳食历史信息失败";
			map.put("message", message);
			dietaryHistory.add(map);
			log.error(message);
		}
		return dietaryHistory;
	}
	@PostMapping("/getDietaryUpload")
	@ResponseBody
	@ApiOperation(value="膳食信息上传")
	public List<Map> getDietaryUpload(){
		List<Map> dietaryUpload = null;
		try {
			dietaryUpload = dietaryService.getDietaryUpload();
		} catch (Exception e) {
			String message = "膳食上传信息失败";
			map.put("message", message);
			dietaryUpload.add(map);
			log.error(message);
		}
		return dietaryUpload;
	}
}
