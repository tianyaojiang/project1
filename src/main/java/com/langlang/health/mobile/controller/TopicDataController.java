package com.langlang.health.mobile.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.mobile.entity.TopicData;
import com.langlang.health.mobile.service.TopicDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* created by xb on Jan 28, 2019
*/
@Api(tags="话题数据")
@RestController
@RequestMapping("/health")
public class TopicDataController {
	@Autowired
	private TopicDataService topicDataService;
	
	
	@ApiOperation(value="获取信息完整度|健康计划进度数据", notes = "获取信息完整度|健康计划进度数据")
	@PostMapping(value = "/getMessData")
	public RestResponseBo getMessData(TopicData topicData) throws Exception{//查询用户话题/数据
		if(topicData.getUserId()==null){
			String message="上传userId不能为空";
			return RestResponseBo.fail(message);
		}
		if(topicData.getMessType() ==null){
			String message="信息类型不能为空";
			return RestResponseBo.fail(message);
		}
		List<TopicData> list = topicDataService.getMessData(topicData);
		return RestResponseBo.ok(list);
	}
	
	
	@ApiOperation(value="新增信息完整度|健康计划进度数据", notes = "新增信息完整度|健康计划进度数据")
	@PostMapping(value = "/addMessData")
	public RestResponseBo addMessData(TopicData topicData) throws Exception{
		if(topicData.getUserId()==null){
			String message="上传userId不能为空";
			return RestResponseBo.fail(message);
		}
		if(topicData.getMessName() ==null){
			String message="信息名称不能为空";
			return RestResponseBo.fail(message);
		}
		if(topicData.getMessType() ==null){
			String message="信息类型不能为空";
			return RestResponseBo.fail(message);
		}
		boolean res = topicDataService.addMessData(topicData);
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	
	@ApiOperation(value="更新信息完整度|健康计划进度数据", notes = "更新信息完整度|健康计划进度数据")
	@PostMapping(value = "/updateMessData")
	public RestResponseBo updateMessData(TopicData topicData) throws Exception{
		if(topicData.getUserId()==null){
			String message="上传userId不能为空";
			return RestResponseBo.fail(message);
		}
		if(topicData.getMessName() ==null){
			String message="信息名称不能为空";
			return RestResponseBo.fail(message);
		}
		if(topicData.getMessType() ==null){
			String message="信息类型不能为空";
			return RestResponseBo.fail(message);
		}
		boolean res = topicDataService.updateMessData(topicData);
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
}
