package com.langlang.health.mine.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.langlang.health.mine.entity.Message;

@Mapper
public interface MessageMapper {
	List<Message> searchMessages();
	int addMessage(Message message);
	int updateMessage(@Param("message") Message message);
	int deleteMessage(Integer id);
}
