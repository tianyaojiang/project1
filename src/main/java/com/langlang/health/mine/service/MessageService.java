package com.langlang.health.mine.service;

import java.util.List;
import com.langlang.health.mine.entity.Message;

public interface MessageService {
	List<Message> searchAll();
	boolean insertMessage(String message, Integer createId, Integer updateId);
	boolean updateMessage(String message, Integer updateId);
	boolean deleteMessage(Integer id);
}
