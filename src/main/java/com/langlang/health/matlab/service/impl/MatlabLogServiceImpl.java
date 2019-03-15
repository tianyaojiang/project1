package com.langlang.health.matlab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.matlab.entity.MatlabLog;
import com.langlang.health.matlab.mapper.MatlabLogMapper;
import com.langlang.health.matlab.service.MatlabLogService;

@Service
public class MatlabLogServiceImpl implements MatlabLogService {

	@Autowired
	private MatlabLogMapper matlabLogMapper;
	
	@Override
	public MatlabLog getCardMatlabLog(String userName, String fileTime) {
		MatlabLog matlabLog = new MatlabLog();
		matlabLog.setUserName(userName);
		matlabLog.setFileType(0);
		matlabLog.setFileTime(fileTime);
		return matlabLogMapper.getMatlabLog(matlabLog);
	}

}
