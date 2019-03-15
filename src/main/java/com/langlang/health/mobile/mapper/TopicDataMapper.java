package com.langlang.health.mobile.mapper;

import java.util.List;

import com.langlang.health.mobile.entity.TopicData;

/**
* created by xb on Jan 28, 2019
*/
public interface TopicDataMapper {

	List<TopicData> getMessData(TopicData topicData);

	boolean addMessData(TopicData topicData);

	boolean updateMessData(TopicData topicData);

}
