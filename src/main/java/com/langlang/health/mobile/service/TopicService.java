package com.langlang.health.mobile.service;

import java.util.List;

import com.langlang.health.mobile.entity.Topic;

public interface TopicService {

	List<Topic> getTopic(Integer userId);

	boolean addTopic(Integer userId);

	boolean updateTopic(Topic topic);

}
