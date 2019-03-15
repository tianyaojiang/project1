package com.langlang.health.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.langlang.health.account.service.SportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
@Api(tags="获取用户运动信息")
@Controller
@RequestMapping("/getSport")
@SuppressWarnings("rawtypes")
@Slf4j
public class SportController {

	@Autowired
	private SportService sportServie;
	private Map<String,String> map = new HashMap<>();
	
	/**
	 * 运动获取
	 * @return
	 */
	@ApiOperation(value="运动获取",notes="")
	@GetMapping("/getSportAll")
	@ResponseBody
	public List<Map> getSportAll(){
		List<Map> sportAll = null;
		try {
			sportAll = sportServie.getSportAll();
		} catch (Exception e) {
			String message = "获取信息失败";
			map.put("message", message);
			sportAll.add(map);
			log.error(message,e);
		}
		return sportAll;
	}
	
	/**
	 * 运动计划获取
	 * @return
	 */
	@PostMapping("/getSportList")
	@ResponseBody
	public List<Map> getSportList(){
		
		List<Map> sportList = null;
		try {
			sportList = sportServie.getSportList();
		} catch (Exception e) {
			String message = "获取运动计划信息失败";
			map.put("message", message);
			sportList.add(map);
			log.error(message,e);
		}
		return sportList;
	}
	/***
	 * 运动上传
	 * @return
	 */
	@PostMapping("/getSportUpload")
	@ResponseBody
	public List<Map> getSportUpload(){
		
		List<Map> sportUpload = null;
		try {
			sportUpload = sportServie.getSportUpload();
		} catch (Exception e) {
			String message = "运动上传失败";
			map.put("message", message);
			sportUpload.add(map);
			log.error(message,e);
		}
		
		return sportUpload;
	}
	
	
	
	
	
	
	
	
}
