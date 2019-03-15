package com.langlang.health.system.service.impl;

import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.langlang.health.common.entity.PageBO;
import com.langlang.health.common.util.DateUtil;
import com.langlang.health.common.util.SendMsgServer;
import com.langlang.health.shiro.ShiroRealm;
import com.langlang.health.system.entity.Role;
import com.langlang.health.system.entity.User;
import com.langlang.health.system.entity.UserDTO;
import com.langlang.health.system.entity.UserRoleKey;
import com.langlang.health.system.entity.UserRolesVO;
import com.langlang.health.system.entity.UserSearch;
import com.langlang.health.system.mapper.RoleMapper;
import com.langlang.health.system.mapper.UserMapper;
import com.langlang.health.system.mapper.UserRoleMapper;
import com.langlang.health.system.service.UserService;

/**
 * Created by tyj on 2018/08/15.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public List<User> getUsers(UserSearch userSearch, int page, int limit) {
		// 时间处理
		if (null != userSearch) {
			if (StringUtils.isNotEmpty(userSearch.getInsertTimeStart())
					&& StringUtils.isEmpty(userSearch.getInsertTimeEnd())) {
				userSearch.setInsertTimeEnd(DateUtil.format(new Date()));
			} else if (StringUtils.isEmpty(userSearch.getInsertTimeStart())
					&& StringUtils.isNotEmpty(userSearch.getInsertTimeEnd())) {
				userSearch.setInsertTimeStart(DateUtil.format(new Date()));
			}
			if (StringUtils.isNotEmpty(userSearch.getInsertTimeStart())
					&& StringUtils.isNotEmpty(userSearch.getInsertTimeEnd())) {
				if (userSearch.getInsertTimeEnd().compareTo(
						userSearch.getInsertTimeStart()) < 0) {
					String temp = userSearch.getInsertTimeStart();
					userSearch
							.setInsertTimeStart(userSearch.getInsertTimeEnd());
					userSearch.setInsertTimeEnd(temp);
				}
			}
		}
		PageBO pageBo = new PageBO();
		PageHelper.startPage(page, limit);
		List<User> urList = userMapper.getUsers(userSearch);
		// 获取分页查询后的数据
		PageInfo<User> pageInfo = new PageInfo<>(urList);
		// 设置获取到的总记录数total：
		pageBo.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
		// 将角色名称提取到对应的字段中
		if (null != urList && urList.size() > 0) {
			for (User ur : urList) {
				List<Role> roles = roleMapper.getRolesByUserId(ur.getId());
				if (null != roles && roles.size() > 0) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < roles.size(); i++) {
						Role r = roles.get(i);
						sb.append(r.getRolename());
						if (i != (roles.size() - 1)) {
							sb.append("，");
						}
					}
					ur.setRolename(sb.toString());
				}
			}
		}
		pageBo.setList(urList);
		return urList;
	}

	@Override
	public String setDelUser(Integer id, Integer isDel, Integer insertUid,
			Integer version) {
		User dataUser = this.userMapper.selectByPrimaryKey(id);

		if (null != dataUser
				&& null != dataUser.getVersion()
				&& !String.valueOf(version).equals(
						String.valueOf(dataUser.getVersion()))) {
			return "操作失败，请您稍后再试";
		}
		User tUser= new User();
		tUser.setId(id);
		return this.userMapper.deleteUser(tUser) == 1 ? "ok"
				: "删除失败，请您稍后再试";
	}

	@Override
	public String setDeleteUser(User user) {
		return this.userMapper.deleteUser(user) == 1 ? "ok"
				: "删除失败，请您稍后再试";
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 30000, rollbackFor = {
			RuntimeException.class, Exception.class })
	public String setUser(User user, String roleIds) {
		int userId;
		if (user.getId() != null) {
			// 判断用户是否已经存在
			User existUser = this.userMapper.findUserByMobile(user.getMobile());
			if (null != existUser
					&& !String.valueOf(existUser.getId()).equals(
							String.valueOf(user.getId()))) {
				return "该手机号已经存在";
			}
			User exist = this.userMapper.findUserByName(user.getUsername());
			if (null != exist
					&& !String.valueOf(exist.getId()).equals(
							String.valueOf(user.getId()))) {
				return "该用户名已经存在";
			}
			User dataUser = this.userMapper.selectByPrimaryKey(user.getId());
			// 版本不一致
			if (null != dataUser
					&& null != dataUser.getVersion()
					&& !String.valueOf(user.getVersion()).equals(
							String.valueOf(dataUser.getVersion()))) {
				return "操作失败，请您稍后再试";
			}
			// 更新用户
			userId = user.getId();
			user.setUpdateTime(new Date());
			// 设置加密密码
			if (StringUtils.isNotBlank(user.getPassword())) {
				user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			}
			this.userMapper.updateByPrimaryKeySelective(user);
			// 删除之前的角色
			List<UserRoleKey> urs = this.userRoleMapper.findByUserId(userId);
			if (null != urs && urs.size() > 0) {
				for (UserRoleKey ur : urs) {
					this.userRoleMapper.deleteByPrimaryKey(ur);
				}
			}
			// 如果是自己，修改完成之后，直接退出；重新登录
			User adminUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (adminUser != null
					&& adminUser.getId().intValue() == user.getId().intValue()) {
				log.debug("更新自己的信息，退出重新登录！adminUser=" + adminUser);
				SecurityUtils.getSubject().logout();
			}

			// 清除ehcache中所有用户权限缓存，必须触发鉴权方法才能执行授权方法doGetAuthorizationInfo
			RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils
					.getSecurityManager();
			ShiroRealm authRealm = (ShiroRealm) rsm.getRealms().iterator()
					.next();
			authRealm.clearCachedAuth();
			log.debug("清除所有用户权限缓存！！！");
		} else {
			// 判断用户是否已经存在
			User existUser = this.userMapper.findUserByMobile(user.getMobile());
			if (null != existUser) {
				return "该手机号已经存在";
			}
			User exist = this.userMapper.findUserByName(user.getUsername());
			if (null != exist) {
				return "该用户名已经存在";
			}
			// 新增用户
			user.setCreateTime(new Date());
			user.setIsDel(0);
			user.setIsLock(0);
			// 设置加密密码
			if (StringUtils.isNotBlank(user.getPassword())) {
				user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			} else {
				user.setPassword(DigestUtils.md5Hex("654321"));
			}
			this.userMapper.insert(user);
			userId = user.getId();
		}
		// 给用户授角色
		String[] arrays = roleIds.split(",");
		for (String roleId : arrays) {
			UserRoleKey urk = new UserRoleKey();
			urk.setRoleId(Integer.valueOf(roleId));
			urk.setUserId(userId);
			this.userRoleMapper.insert(urk);
		}
		return "ok";
	}


    /**
	 * 设置禁用/启用用户
	 * */
	@Override
	public String setLockUser(User user) {
		User dataUser = this.userMapper.selectByPrimaryKey(user.getId());
		// 版本不一致
		if (null != dataUser
				&& null != dataUser.getVersion()
				&& !String.valueOf(user.getVersion()).equals(
						String.valueOf(dataUser.getVersion()))) {
			return "操作失败，请您稍后再试";
		}
		return this.userMapper.setLockUser(user) == 1 ? "ok"
				: "操作失败，请您稍后再试";
	}

	@Override
	public UserRolesVO getUserAndRoles(Integer id) {
		// 获取用户及他对应的roleIds
		return this.userMapper.getUserAndRoles(id);

	}

	@Override
	public String sendMsg(UserDTO user) {
		// 校验用户名和密码 是否正确
		User existUser = this.userMapper.findUser(user.getUsername(),
				DigestUtils.md5Hex(user.getPassword()));
		if (null != existUser && existUser.getMobile().equals(user.getMobile())) {
			String mobileCode = "";
			if (existUser.getSendTime() != null) {
				long beginTime = existUser.getSendTime().getTime();
				long endTime = new Date().getTime();
				// 1分钟内有效
				if (((endTime - beginTime) < 60000)) {
					log.debug("发送短信验证码【UserServiceImpl.sendMsg】用户信息=existUser:"
							+ existUser);
					mobileCode = existUser.getMcode();
				}
			}
			if (StringUtils.isBlank(mobileCode)) {
				// 1分钟以内，有效
				mobileCode = String
						.valueOf((int) ((Math.random() * 9 + 1) * 100000));
				// 保存短信
				existUser.setMcode(mobileCode);
			}
			// 更新验证码时间，延长至当前时间
			existUser.setSendTime(new Date());
			this.userMapper.updateByPrimaryKeySelective(existUser);
			// 发送短信验证码 ok、no
			return SendMsgServer.SendMsg(mobileCode + "(验证码)，如不是本人操作，请忽略此消息。",
					user.getMobile());
		} else {
			return "您输入的用户信息有误，请您重新输入";
		}
	}

	@Override
	public User findUserByMobile(String mobile) {
		return this.userMapper.findUserByMobile(mobile);
	}

	@Override
	public User findUserByName(String username) {
		return this.userMapper.findUserByName(username);
	}

	@Override
	public String sendMessage(int userId, String mobile) {
		String mobile_code = String.valueOf((Math.random() * 9 + 1) * 100000);
		// 保存短信
		User user = new User();
		user.setId(userId);
		user.setMcode(mobile_code);
		user.setSendTime(new Date());
		this.userMapper.updateByPrimaryKeySelective(user);
		// 发送短信验证码 ok、no
		return SendMsgServer.SendMsg(mobile_code + "(验证码)，如不是本人操作，请忽略此消息。",
				user.getMobile());
	}

	@Override
	public int updatePwd(Integer id, String password) {
		return this.userMapper.updatePwd(id, password);
	}

	@Override
	public int setUserLockNum(Integer id, int isLock) {
		return this.userMapper.setUserLockNum(id, isLock);
	}

	@Override
	public List<User> searchRelations(Integer userId, Integer relationType) {
		return userMapper.searchRelations(userId, relationType);
	}

	@Override
	public User findUserById(Integer userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public int UpdateWeight(Integer id, Integer weight) {
		// TODO Auto-generated method stub
		return userMapper.updateWeight(id, weight);
	}
}
