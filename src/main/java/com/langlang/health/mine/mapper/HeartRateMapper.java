package com.langlang.health.mine.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.langlang.health.mine.entity.HeartRate;

@Mapper
public interface HeartRateMapper {
	HeartRate searchHeartRateByUser(@Param("userId")Integer userId, @Param("fileName")String fileName, @Param("fileType")Integer fileType);
}
