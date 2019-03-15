package com.langlang.health.system.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.langlang.health.system.entity.RolePermissionKey;

/**
 * Created by tyj on 2018/08/14.
 */
@Mapper
public interface RolePermissionMapper {
    int deleteByPrimaryKey(RolePermissionKey key);

    int insert(RolePermissionKey record);

    int insertSelective(RolePermissionKey record);

	List<RolePermissionKey> findByRole(int roleId);
}