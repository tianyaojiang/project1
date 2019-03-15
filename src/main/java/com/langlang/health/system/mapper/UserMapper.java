package com.langlang.health.system.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.langlang.health.system.entity.User;
import com.langlang.health.system.entity.UserRolesVO;
import com.langlang.health.system.entity.UserSearch;

/**
 * Created by tyj on 2018/08/14.
 */
@Mapper
public interface UserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
	
	int updateWeight(@Param("id") Integer id, @Param("weight") Integer weight);

	/**
	 * 分页查询用户数据
	 * @return
	 */
	List<User> getUsers(@Param("userSearch") UserSearch userSearch);

	/**
	 * 删除用户
	 * @param user
	 * @param
	 * @return
	 */

	int deleteUser(User user);
	/**
	 * 设置用户
	 * @param user
	 * @param
	 * @return
	 */
	int setLockUser(User user);




	/**
	 * 查询用户及对应的角色
	 * @param id
	 * @return
	 */
	UserRolesVO getUserAndRoles(Integer id);

	/**
	 * 根据用户名和密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	User findUser(@Param("username") String username,
                  @Param("password") String password);

	/**
	 *	根据手机号获取用户数据
	 * @param mobile
	 * @return
	 */
	User findUserByMobile(String mobile);

	/**
	 * 根据用户名获取用户数据
	 * @param username
	 * @return
	 */
	User findUserByName(String username);

	/**
	 * 修改用户密码
	 * @param id
	 * @param password
	 * @return
	 */
	int updatePwd(@Param("id") Integer id, @Param("password") String password);

	/**
	 * 是否锁定用户
	 * @param id
	 * @param isLock
	 * @return
	 */
	int setUserLockNum(@Param("id") Integer id, @Param("isLock") int isLock);
	
	/**
	 * 获取用户的关联用户
	 * @param userId
	 * @param relationType
	 * @return
	 */
	List<User> searchRelations(@Param("userId") Integer userId, @Param("relationType") Integer relationType);
}