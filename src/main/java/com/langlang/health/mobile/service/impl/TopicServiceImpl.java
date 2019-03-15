package com.langlang.health.mobile.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.mobile.entity.Topic;
import com.langlang.health.mobile.mapper.HealthDangerfilterMapper;
import com.langlang.health.mobile.mapper.TopicMapper;
import com.langlang.health.mobile.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicMapper topicMapper;
	
	@Override
	public List<Topic> getTopic(Integer userId) {
		Topic topic = new Topic();
		topic.setUserId(userId);
		return topicMapper.getTopic(topic);
	}

	@Override
	public boolean addTopic(Integer userId) {
		ArrayList<Topic> list = new ArrayList<Topic>();
		Date date = new Date();
		Topic t1 = new Topic(userId,"高血压","green",0,1,date,date);
		Topic t2 = new Topic(userId,"糖尿病","green",0,1,date,date);
		Topic t3 = new Topic(userId,"信息完整度","green",0,0,date,date);
		Topic t4 = new Topic(userId,"健康计划进度","green",0,0,date,date);
		Topic t5 = new Topic(userId,"心血管","green",0,1,date,date);
		Topic t6 = new Topic(userId,"脑血管","green",0,1,date,date);
		list.add(t1);
		list.add(t2);
		list.add(t3);
		list.add(t4);
		list.add(t5);
		list.add(t6);
		return topicMapper.addTopic(list);
	}

	@Override
	public boolean updateTopic(Topic topic) {
		topic.setUpdateTime(new Date());
		return topicMapper.updateTopic(topic);
	}



}
