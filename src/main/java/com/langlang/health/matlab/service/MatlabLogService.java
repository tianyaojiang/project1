package com.langlang.health.matlab.service;

import com.langlang.health.matlab.entity.MatlabLog;

public interface MatlabLogService {

	MatlabLog getCardMatlabLog(String userName, String fileTime);
	
}
