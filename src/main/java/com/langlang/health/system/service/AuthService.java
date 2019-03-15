package com.langlang.health.system.service;

import java.util.List;
import com.langlang.health.system.entity.Permission;
import com.langlang.health.system.entity.PermissionVO;
import com.langlang.health.system.entity.Role;
import com.langlang.health.system.entity.RolePermissionVO;

/**
 * Created by tyj on 2018/08/15.
 */
public interface AuthService {

	int addPermission(Permission permission);

	List<Permission> permList();

	int updatePerm(Permission permission);

	Permission getPermission(int id);

	String delPermission(int id);

	/**
	 * 根据用户ID获取角色列表
	 * @return
	 */
	List<Role> getRolesByUserId(Integer userId);
	/**
	 * 根据用户ID获取权限列表
	 * @return
	 */
	List<Permission> getPermsByUserId(Integer userId);

	/**
	 * 查询所有角色
	 * @return
	 */
	List<Role> roleList();

	/**
	 * 查询角色信息
	 * @return
	 */
	Role selectByPrimaryKey(Integer id);


	/**
	 * 关联查询权限树列表
	 * @return
	 */
	List<PermissionVO> findPerms();

	/**
	 * 添加角色
	 * @param role
	 * @param permIds
	 * @return
	 */
	String addRole(Role role, String permIds);

	/**
	 * 根据角色id查询角色信息及权限信息
	 * @return
	 */
	List<RolePermissionVO> findRoleAndPerms(Integer id);

	/**
	 * 更新角色并授权
	 * @param role
	 * @param permIds
	 * @return
	 */
	String updateRole(Role role, String permIds);

	/**
	 * 删除角色以及它对应的权限
	 * @param id
	 * @return
	 */
	String delRole(int id);

	/**
	 * 查找所有角色
	 * @return
	 */
	List<Role> getRoles();


	/**
	 * 根据角色id获取权限数据
	 * @param id
	 * @return
	 */
	List<Permission> findPermsByRoleId(Integer id);


}
