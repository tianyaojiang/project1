package com.langlang.health.mine.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.langlang.health.mine.entity.Message;
import com.langlang.health.mine.mapper.MessageMapper;
import com.langlang.health.mine.service.MessageService;

import lombok.val;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageMapper messageMapper;

	@Override
	public List<Message> searchAll() {
		return messageMapper.searchMessages();
	}

	@Override
	public boolean deleteMessage(Integer id) {
		boolean result = true;
		int value = messageMapper.deleteMessage(id);
		if(value != 1){
			result = false;
		}
		return result;
	}

	@Override
	public boolean insertMessage(String message, Integer createId, Integer updateId) {
		boolean result = false;
		Message param = new Message();
		param.setMessage(message);
		param.setCreateId(createId);
		param.setUpdateId(updateId);
		Date date = new Date();
		param.setCreateTime(date);
		param.setUpdateTime(date);
		int value = messageMapper.addMessage(param);
		if(value == 1){
			result = true;
		}
		return result;
	}

	@Override
	public boolean updateMessage(String message, Integer updateId) {
		boolean result = false;
		Message param = new Message();
		param.setMessage(message);
		param.setUpdateId(updateId);
		param.setUpdateTime(new Date());
		int value = messageMapper.updateMessage(param);
		if(value == 1){
			result = true;
		}
		return result;
	}
	
}
