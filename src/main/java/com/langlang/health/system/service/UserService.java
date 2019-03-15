package com.langlang.health.system.service;


import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.system.entity.User;
import com.langlang.health.system.entity.UserDTO;
import com.langlang.health.system.entity.UserRolesVO;
import com.langlang.health.system.entity.UserSearch;

import java.util.List;

/**
 * Created by tyj on 2018/08/15.
 */
public interface UserService {


	/**
	 * 分页查询用户列表
	 * @param page
	 * @param limit
	 * @return
	 */
	 List<User> getUsers(UserSearch userSearch, int page, int limit);

	/**
	 *	设置用户【新增或更新】
	 * @param user
	 * @param roleIds
	 * @return
	 */
	String setUser(User user, String roleIds);

	/**
	 * 设置用户是否禁用

	 * @return
	 */
	String setLockUser(User user);

	/**
	 * 删除用户
	 * @param id
	 * @param isDel
	 * @return
	 */
	String setDelUser(Integer id, Integer isDel, Integer insertUid,
                      Integer version);

	String   setDeleteUser(User user);

	/**
	 * 查询用户数据
	 * @param id
	 * @return
	 */
	UserRolesVO getUserAndRoles(Integer id);

	/**
	 * 发送短信验证码
	 * @param user
	 * @return
	 */
	String sendMsg(UserDTO user);

	/**
	 * 根据手机号查询用户数据
	 * @param mobile
	 * @return
	 */
	User findUserByMobile(String mobile);

	/**
	 * 根据手机号查询用户数据
	 * @param username
	 * @return
	 */
	User findUserByName(String username);

	/**
	 * 根据手机号发送短信验证码
	 * @param mobile
	 * @return
	 */
	String sendMessage(int userId, String mobile);

	/**
	 * 修改用户手机号
	 * @param id
	 * @param password
	 * @return
	 */
	int updatePwd(Integer id, String password);

	/**
	 * 锁定用户
	 * @param id
	 * @param isLock  0:解锁；1：锁定
	 * @return
	 */
	int setUserLockNum(Integer id, int isLock);
	/**
	 * 更新用户的体重信息
	 * @param id
	 * @param weight
	 * @return
	 */
	int UpdateWeight(Integer id, Integer weight);
	
	/**
	 * 获取用户的关联账号
	 * @param userId
	 * @param relationType
	 * @return
	 */
	List<User> searchRelations(Integer userId, Integer relationType);
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	User findUserById(Integer userId);
}
