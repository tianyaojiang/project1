package com.langlang.health.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.langlang.health.system.entity.UserRoleKey;

/**
 * Created by tyj on 2018/08/14.
 */
@Mapper
public interface UserRoleMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);

	/**
	 * 根据用户获取用户角色中间表数据
	 * @param userId
	 * @return
	 */
	List<UserRoleKey> findByUserId(int userId);
}