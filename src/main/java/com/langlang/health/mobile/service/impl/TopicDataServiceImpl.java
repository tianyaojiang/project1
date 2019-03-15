package com.langlang.health.mobile.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.mobile.entity.TopicData;
import com.langlang.health.mobile.mapper.TopicDataMapper;
import com.langlang.health.mobile.mapper.TopicMapper;
import com.langlang.health.mobile.service.TopicDataService;

/**
* created by xb on Jan 28, 2019
*/
@Service
public class TopicDataServiceImpl implements TopicDataService{

	@Autowired
	private TopicDataMapper topicDataMapper;
	
	@Override
	public List<TopicData> getMessData(TopicData topicData) {
	   return topicDataMapper.getMessData(topicData);
	}

	@Override
	public boolean addMessData(TopicData topicData) {
		topicData.setCreateTime(new Date());
		return topicDataMapper.addMessData(topicData);
	}

	@Override
	public boolean updateMessData(TopicData topicData) {
		return topicDataMapper.updateMessData(topicData);
	}

}
