package com.langlang.health.mobile.mapper;

import java.util.ArrayList;
import java.util.List;

import com.langlang.health.mobile.entity.Topic;

public interface TopicMapper {

	List<Topic> getTopic(Topic topic);

	boolean addTopic(ArrayList<Topic> list);

	boolean updateTopic(Topic topic);


}
