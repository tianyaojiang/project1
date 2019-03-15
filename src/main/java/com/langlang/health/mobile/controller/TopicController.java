package com.langlang.health.mobile.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.mobile.entity.Topic;
import com.langlang.health.mobile.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


@Api(tags="话题列表")
@RestController
@RequestMapping("/health")
public class TopicController {
	@Autowired
	private TopicService topicService;
	
	
	@ApiOperation(value="展示用户话题", notes = "展示用户话题")
	@PostMapping(value = "/getTopic")
	public RestResponseBo getTopic(@RequestParam("userId") Integer userId) throws Exception{//查询用户话题/数据
		List<Topic> topic = topicService.getTopic(userId);
		if(topic.size()==0){
			topicService.addTopic(userId);
			topic = topicService.getTopic(userId);
		}
		return RestResponseBo.ok(topic);
	}
	
	
	@ApiOperation(value="新增用户话题", notes = "新增用户话题")
	@PostMapping(value = "/addTopic")
	public RestResponseBo addTopic(@RequestParam("userId") Integer userId) throws Exception{
		boolean res = topicService.addTopic(userId);
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	
	@ApiOperation(value="更新用户话题", notes = "更新用户话题")
//	@ApiImplicitParam(name = "user_id", required = true, dataType = "Integer")
	@PostMapping(value = "/updateTopic")
	public RestResponseBo updateTopic(Topic topic) throws Exception{
		if(topic.getUserId()==null){
			String message="上传userId不能为空";
			return RestResponseBo.fail(message);
		}
		boolean res = topicService.updateTopic(topic);
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	
}
