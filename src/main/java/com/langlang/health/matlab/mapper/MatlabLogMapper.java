package com.langlang.health.matlab.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.langlang.health.matlab.entity.MatlabLog;

@Mapper
public interface MatlabLogMapper {

	MatlabLog getMatlabLog(MatlabLog matlabLog);
	
}
