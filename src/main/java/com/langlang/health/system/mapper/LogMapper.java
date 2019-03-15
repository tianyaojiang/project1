package com.langlang.health.system.mapper;

import com.langlang.health.system.entity.LogVo;
import org.apache.ibatis.annotations.Mapper;

import com.langlang.health.system.entity.Log;

@Mapper
public interface LogMapper {
    int deleteByPrimaryKey(Short id);

    int insert(LogVo record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);



}