package com.langlang.health.system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.langlang.health.system.entity.Userpermission;

@Mapper
public interface UserpermissionMapper {
    int deleteByPrimaryKey(Short id);

    int insert(Userpermission record);

    int insertSelective(Userpermission record);

    Userpermission selectByPrimaryKey(Short id);

    int updateByPrimaryKeySelective(Userpermission record);

    int updateByPrimaryKey(Userpermission record);
}