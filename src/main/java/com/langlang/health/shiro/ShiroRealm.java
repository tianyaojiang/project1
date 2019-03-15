package com.langlang.health.shiro;


import java.util.List;

import com.langlang.health.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.system.entity.Permission;
import com.langlang.health.system.entity.Role;
import com.langlang.health.system.entity.User;
import com.langlang.health.system.service.AuthService;

/**
 * Created by tyj on 2018/08/15.
 */
@Service
@Slf4j
public class ShiroRealm extends AuthorizingRealm {


	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;


	/**
	 * 使用JWT替代原生Token
	 *
	 * @param token
	 * @return
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}


	/**
	 * 授予角色和权限
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {
		String username = JwtUtil.getUsername(principalCollection.toString());
//		User user = userService.findByUserName(username);
		// null usernames are invalid
		if (principalCollection == null) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		//授权
		log.debug("授予角色和权限");
		// 添加权限 和 角色信息
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// 获取当前登陆用户
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
    		if (user.getUsername().equals("admin")) {
			// 超级管理员，添加所有角色、添加所有权限
			authorizationInfo.addRole("*");
			authorizationInfo.addStringPermission("*");
		} else {
			// 普通用户，查询用户的角色，根据角色查询权限
			Integer userId = user.getId();
			List<Role> roles = this.authService.getRolesByUserId(userId);
			if (null != roles && roles.size() > 0) {
				for (Role role : roles) {
					authorizationInfo.addRole(role.getCode());
					// 角色对应的权限数据
					List<Permission> perms = this.authService.findPermsByRoleId(role
							.getId());
					if (null != perms && perms.size() > 0) {
						// 授权角色下所有权限
						for (Permission perm : perms) {
							authorizationInfo.addStringPermission(perm
									.getCode());
						}
					}
				}
			}
		}
		return authorizationInfo;
	}

	/**
	 * 登录认证
	 * @param authenticationToken
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// UsernamePasswordToken用于存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
		log.info("用户登录认证：验证当前Subject时获取到token为：" + ReflectionToStringBuilder
				.toString(token, ToStringStyle.MULTI_LINE_STYLE));
		String username = (String)token.getPrincipal();
		String password = token.getCredentials().toString();
		User user = userService.findUserByName(username);
		log.info("用户登录认证！用户信息user：" + user);
		log.info("用户登录认证！用户信息user.getPassword：" + user.getPassword());
		log.info("用户登录认证！用户信息user加密的password：" + DigestUtils.md5Hex(user.getPassword()));
		if (user == null) {
			return null;
		} else {
			List<Role> roles = authService.getRolesByUserId(user.getId());
			List<Permission> perms = authService.getPermsByUserId(user.getId());
			user.getRoles().addAll(roles);
			user.getPerms().addAll(perms);
			log.info("用户信息user：" + user);
			return new SimpleAuthenticationInfo(user, user.getPassword(),
					getName());
		}
	}

	/**
	 * 清除所有缓存
	 */
	public void clearCachedAuth(){
		this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}
}
