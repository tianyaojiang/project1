package com.langlang.health.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.common.exception.HealthException;
import com.langlang.health.common.util.LogActions;
import com.langlang.health.system.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.langlang.health.common.util.StateEnum;
import com.langlang.health.common.util.IStatusMessage;
import com.langlang.health.common.util.ValidateUtil;
import com.langlang.health.system.entity.Role;
import com.langlang.health.system.entity.User;
import com.langlang.health.system.entity.UserDTO;
import com.langlang.health.system.entity.UserRolesVO;
import com.langlang.health.system.entity.UserSearch;
import com.langlang.health.system.service.AuthService;
import com.langlang.health.system.service.UserService;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tyj on 2018/08/15.
 */
@Api(tags="用户管理")
@RestController
@RequestMapping("")
@Slf4j
public class UserController {


	@Autowired
	private UserService userService;
	@Autowired
	private AuthService authService;
//	@Autowired
//	private EhCacheManager ecm;

	//private static final Pattern MOBILE_PATTERN = Pattern.compile("^1\\d{10}$");
	@Autowired
	private LogService logService;
	
	@ApiOperation(value="查询用户信息")
	@GetMapping("/user/{id}")
	public RestResponseBo searchUser(@PathVariable("id") Integer id){
		String message = "该用户不存在，请您联系管理员";
		User user = userService.findUserById(id);
		return RestResponseBo.ok(user);
	}

	@ApiOperation(value="登录", notes="用户登录")
	@ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public RestResponseBo login(
			User user,
			@RequestParam(value = "rememberMe", required = false) boolean rememberMe ,HttpServletRequest request, HttpServletResponse response) {
		log.debug("用户登录，请求参数=user:" + user + "，是否记住我：" + rememberMe);

		String message;
		if (null == user) {
			 message = "请求参数有误，请您稍后再试";
			log.error("用户登录，结果=responseResult:" + message);
			return RestResponseBo.fail(message);
		}

		int ss = request.getSession().getMaxInactiveInterval();
		log.info("session:"+ss);
		// 用户是否存在
		User existUser = this.userService.findUserByName(user.getUsername());
		if (existUser == null) {
			message = "该用户不存在，请您联系管理员";
			log.debug("用户登录，结果=responseResult:" + message);
			return RestResponseBo.fail(message);
		} else {
			//TODO:验证码待实现
			// 校验验证码
			/*if(!existUser.getMcode().equals(user.getSmsCode())){ //不等
			 responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
			 responseResult.setMessage("短信验证码输入有误");
			 log.debug("用户登录，结果=responseResult:"+responseResult);
			 return responseResult;
			} //1分钟
			long beginTime =existUser.getSendTime().getTime();
			long endTime = new Date().getTime();
			if(((endTime-beginTime)-60000>0)){
				 responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
				 responseResult.setMessage("短信验证码超时");
				 log.debug("用户登录，结果=responseResult:"+responseResult);
				 return responseResult;
			}*/
		}
		// 用户登录
		try {
			// 生成token
			UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername().trim(), user.getPassword().trim(), rememberMe);
			// 验证
			Subject subject = SecurityUtils.getSubject();
			// 添加认证：
			 subject.login(token);
            // 从session取出用户信息
			User userInfo = (User) subject.getPrincipal();
			log.info("用户登录，user=" + user.getMobile() + ",登录结果=responseResult:"+"登录成功");
			return RestResponseBo.ok(userInfo, 1);
		} catch (UnknownAccountException uae) {
			log.error("用户登录，用户验证未通过：未知用户！user=" + user.getUsername(), uae);
			message = "该用户不存在，请您联系管理员";
			return RestResponseBo.fail(message);
		} catch (IncorrectCredentialsException ice) {
			// 获取输错次数
			log.error("用户登录，用户验证未通过：错误的凭证，密码输入错误！user=" + user.getMobile(),
					ice);
			message = "用户名或密码不正确";
			return RestResponseBo.fail(message);
		} catch (LockedAccountException lae) {
			log.error("用户登录，用户验证未通过：账户已锁定！user=" + user.getMobile(), lae);
			message = "账户已锁定";
			return RestResponseBo.fail(message);
		} catch (ExcessiveAttemptsException eae) {
			log.error("用户登录，用户验证未通过：错误次数大于5次,账户已锁定！user=.getMobile()" + user, eae);
			message = "用户名或密码错误次数大于5次,账户已锁定!2分钟后可再次登录，或联系管理员解锁";
			return RestResponseBo.fail(message);
		} catch (DisabledAccountException sae){
			log.error("用户登录，用户验证未通过：帐号已经禁止登录！user=" + user.getMobile(),sae);
			message = "帐号已经禁止登录";
			return RestResponseBo.fail(message);
		}catch (AuthenticationException ae) {
			log.error("用户登录，用户验证未通过：认证异常，异常信息如下！user=" + user.getMobile(),
					ae);
			message = "用户名或密码不正确";
			return RestResponseBo.fail(message);
		} catch (Exception e) {
			log.error("用户登录，用户验证未通过：操作异常，异常信息如下！user=" + user.getMobile(), e);
			message = "用户登录失败，请您稍后再试";
			return RestResponseBo.fail(message);
		}
//		Cache<String, AtomicInteger> passwordRetryCache = ecm
//				.getCache("passwordRetryCache");
//		if (null != passwordRetryCache) {
//			int retryNum = (passwordRetryCache.get(existUser.getMobile()) == null ? 0
//					: passwordRetryCache.get(existUser.getMobile())).intValue();
//			log.debug("输错次数：" + retryNum);
//			if (retryNum > 0 && retryNum < 6) {
//				responseResult.setMessage("用户名或密码错误" + retryNum + "次,再输错"
//						+ (6 - retryNum) + "次账号将锁定");
//			}
//		}


	}


	@ApiOperation(value="注册", notes="注册新用户")
	@ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public RestResponseBo<?> register( User user, HttpServletRequest request, HttpServletResponse response) {
		try {
			user.setMobile(user.getUsername());
			String roleIds = "1";//默认设置为普通用户
			String message = "";
			if (null == user) {
				log.debug("设置用户[新增或更新]，结果=请您填写用户信息");
				message = "请您填写用户信息";
				return RestResponseBo.fail(message);
			}

			// 设置用户[新增或更新]
			String register = userService.setUser(user, roleIds);
			logService.insertLog(LogActions.REGINDEX.getAction(), null, request.getRemoteAddr(), user.getId());
			log.info("恭喜你，注册成功了！");
			return RestResponseBo.ok(register, 1);
		} catch (Exception e) {
			String message = "呜呜,注册失败！";
			if (e instanceof HealthException) {
				message = e.getMessage();
			} else {
				log.error(message, e);
			}
			return RestResponseBo.fail(message);
		}

	}
	
	
	



	/**
	 * 分页查询用户列表
	 * @return ok/fail
	 */
	@RequestMapping(value = "/getUsers", method = RequestMethod.POST)
	@ResponseBody
//	@RequiresPermissions(value = "user:manage")
	public RestResponseBo getUsers(@RequestParam("page") Integer page,
								   @RequestParam("limit") Integer limit, UserSearch userSearch) {
		log.debug("分页查询用户列表！搜索条件：userSearch：" + userSearch + ",page:" + page
				+ ",每页记录数量limit:" + limit);
		List<User> userList = new ArrayList<>();
		try {
			if (null == page) {
				page = 1;
			}
			if (null == limit) {
				limit = 10;
			}
			// 获取用户和角色列表
			userList = userService.getUsers(userSearch, page, limit);
			log.info("用户列表查询=pdr:" + userList);
			return RestResponseBo.ok(userList,1);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("用户列表查询异常！", e);
			return RestResponseBo.fail();
		}


	}

	/**
	 * 设置用户状态（禁用）
	 * @return ok/fail
	 */
	@RequestMapping(value = "/lockUser", method = RequestMethod.POST)
	@ResponseBody
	public String setLockUser(@RequestParam("data") String userData) {
		log.debug("设置用户是否,！userData："+userData);
        List<Map> list= JSON.parseArray(userData, Map.class);
		String msg = "";
		try {
			if (list.size()==0) {
				log.debug("设置用户是否，结果=请求参数有误，请您稍后再试");
				return "请求参数有误，请您稍后再试";
			}
			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				log.debug("设置用户是否结果=您未登录或登录超时，请您登录后再试");
				return "您未登录或登录超时，请您登录后再试";
			}

			// 设置用户禁用,
            list.forEach(v->{
				User user = new User();
				user.setId((Integer) v.get("id"));
				user.setIsLock(StateEnum.DISABLE.getKey());
				user.setVersion((Integer) v.get("version"));
				userService.setLockUser(user);
			});

            msg ="锁定成功！";
			log.info("操作的用户ID=" + existUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("设置用户是否,异常！", e);
			msg = "操作异常，请您稍后再试！";
		}
		return msg;
	}

	/**
	 * 设置用户状态（启用）
	 * @return ok/fail
	 */
	@RequestMapping(value = "/unlockUser", method = RequestMethod.POST)
	@ResponseBody
	public String setUnlockUser(@RequestParam("data") String userData) {
		log.debug("设置用户是否,！userData："+userData);
		List<Map> list= JSON.parseArray(userData, Map.class);
		String msg = "";
		try {
			if (list.size()==0) {
				log.debug("设置用户是否，结果=请求参数有误，请您稍后再试");
				return "请求参数有误，请您稍后再试";
			}
			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				log.debug("设置用户是否结果=您未登录或登录超时，请您登录后再试");
				return "您未登录或登录超时，请您登录后再试";
			}

			// 设置用户启用,
			list.forEach(v->{
				User user = new User();
				user.setId((Integer) v.get("id"));
				user.setIsLock(StateEnum.ENABLE.getKey());
				user.setVersion((Integer) v.get("version"));
				userService.setLockUser(user);
			});

			msg ="解锁成功！";
			log.info("操作的用户ID=" + existUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("设置用户是否,异常！", e);
			msg = "操作异常，请您稍后再试！";
		}
		return msg;
	}


	/**
	 * 删除用户deleteUser
	 * @return ok/fail
	 */
	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	@ResponseBody
	public String delUser(@RequestParam("id") Integer id,
			@RequestParam("version") Integer version) {
		log.debug("删除用户！id:" + id + ",version:" + version);
		String msg = "";
		try {
			if (null == id || null == version) {
				log.debug("删除用户，结果=请求参数有误，请您稍后再试");
				return "请求参数有误，请您稍后再试";
			}
			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				log.debug("删除用户，结果=您未登录或登录超时，请您登录后再试");
				return "您未登录或登录超时，请您登录后再试";
			}
			// 删除用户
			msg = userService.setDelUser(id, 1, existUser.getId(), version);
			log.info("删除用户:" + msg + "！userId=" + id + "，操作用户id:"
					+ existUser.getId() + ",version:" + version);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除用户异常！", e);
			msg = "操作异常，请您稍后再试";
		}
		return msg;
	}

	/**
	 * 批量删除
	 * @return ok/fail
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public String deleteUser(@RequestParam("data") String userData) {
		log.debug("需要删除的用户信息userData："+userData);
		List<Map> list= JSON.parseArray(userData, Map.class);
		String msg = "";
		try {
			if (list.size()==0) {
				log.debug("设置用户是否，结果=请求参数有误，请您稍后再试");
				return "请求参数有误，请您稍后再试";
			}
			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				log.debug("删除用户，结果=您未登录或登录超时，请您登录后再试");
				return "您未登录或登录超时，请您登录后再试";
			}

			/**删除用户*/
			list.forEach(v->{
				User user = new User();
				user.setId((Integer) v.get("id"));
				user.setVersion((Integer) v.get("version"));
				userService.setDeleteUser(user);
			});

			msg ="删除成功！";
			log.info("操作的用户ID=" + existUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("设置用户是否,异常！", e);
			msg = "操作异常，请您稍后再试！";
		}
		return msg;
	}


	/**
	 * 恢复用户
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/recoverUser", method = RequestMethod.POST)
	@ResponseBody
	public String recoverUser(@RequestParam("id") Integer id,
			@RequestParam("version") Integer version) {
		log.debug("恢复用户！id:" + id + ",version:" + version);
		String msg = "";
		try {
			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (null == existUser) {
				return "您未登录或登录超时，请您登录后再试";
			}
			if (null == id || null == version) {
				return "请求参数有误，请您稍后再试";
			}
			// 删除用户
			msg = userService.setDelUser(id, 0, existUser.getId(), version);
			log.info("恢复用户【" + this.getClass().getName() + ".recoverUser】"
					+ msg + "。用户userId=" + id + "，操作的用户ID=" + existUser.getId() + ",version:" + version);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("恢复用户【" + this.getClass().getName()
					+ ".recoverUser】用户异常！", e);
			msg = "操作异常，请您稍后再试";
		}
		return msg;
	}

	/**
	 * 查询用户数据
	 * @return map
	 */
	@RequestMapping(value = "/getUserAndRoles", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUserAndRoles(@RequestParam("id") Integer id) {
		log.debug("查询用户数据！id:" + id);
		Map<String, Object> map = new HashMap<>();
		try {
			if (null == id) {
				log.debug("查询用户数据==请求参数有误，请您稍后再试");
				map.put("msg", "请求参数有误，请您稍后再试");
				return map;
			}
			// 查询用户
			UserRolesVO urvo = userService.getUserAndRoles(id);
			log.debug("查询用户数据！urvo=" + urvo);
			if (null != urvo) {
				map.put("user", urvo);
				// 获取全部角色数据
				List<Role> roles = this.authService.getRoles();
				log.debug("查询角色数据！roles=" + roles);
				if (null != roles && roles.size() > 0) {
					map.put("roles", roles);
				}
				map.put("msg", "ok");
			} else {
				map.put("msg", "查询用户信息有误，请您稍后再试");
			}
			log.debug("查询用户数据成功！map=" + map);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", "查询用户错误，请您稍后再试");
			log.error("查询用户数据异常！", e);
		}
		return map;
	}

//	/**
//	 * 发送短信验证码
//	 * @param user
//	 * @return
//	 */
//	@RequestMapping(value = "sendMsg", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseResult sendMsg(UserDTO user) {
//		log.debug("发送短信验证码！user:" + user);
//		ResponseResult responseResult = new ResponseResult();
//		try {
//			if (null == user) {
//				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
//						.getCode());
//				responseResult.setMessage("请求参数有误，请您稍后再试");
//				log.debug("发送短信验证码，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			if (!validatorRequestParam(user, responseResult)) {
//				log.debug("发送短信验证码，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			// 送短信验证码
//			// String msg=userService.sendMsg(user);
//			String msg = "ok";
//			if (msg != "ok") {
//				responseResult.setCode(IStatusMessage.SystemStatus.ERROR
//						.getCode());
//				responseResult.setMessage(msg == "no" ? "发送验证码失败，请您稍后再试" : msg);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
//			responseResult.setMessage("发送短信验证码失败，请您稍后再试");
//			log.error("发送短信验证码异常！", e);
//		}
//		log.debug("发送短信验证码，结果=responseResult:" + responseResult);
//		return responseResult;
//	}
//
//
//
//	/**
//	 * 登录【使用redis和mysql实现，用户密码输错次数限制，和锁定解锁用户的功能//TODO】
//	 * @param user
//	 * @param rememberMe
//	 * @return
//	 */
//	@RequestMapping(value = "logina", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseResult logina(
//			UserDTO user,
//			@RequestParam(value = "rememberMe", required = false) boolean rememberMe) {
//		log.debug("用户登录，请求参数=user:" + user + "，是否记住我：" + rememberMe);
//		ResponseResult responseResult = new ResponseResult();
//		responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
//		if (null == user) {
//			responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
//					.getCode());
//			responseResult.setMessage("请求参数有误，请您稍后再试");
//			log.debug("用户登录，结果=responseResult:" + responseResult);
//			return responseResult;
//		}
//		if (!validatorRequestParam(user, responseResult)) {
//			log.debug("用户登录，结果=responseResult:" + responseResult);
//			return responseResult;
//		}
//		// 用户是否存在
//		User existUser = this.userService.findUserByMobile(user.getMobile());
//		if (existUser == null) {
//			responseResult.setMessage("该用户不存在，请您联系管理员");
//			log.debug("用户登录，结果=responseResult:" + responseResult);
//			return responseResult;
//		} else {
//			// 校验验证码
//			/*if(!existUser.getMcode().equals(user.getSmsCode())){ //不等
//			 responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
//			 responseResult.setMessage("短信验证码输入有误");
//			 log.debug("用户登录，结果=responseResult:"+responseResult);
//			 return responseResult;
//			} //1分钟
//			long beginTime =existUser.getSendTime().getTime();
//			long endTime = new Date().getTime();
//			if(((endTime-beginTime)-60000>0)){
//				 responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
//				 responseResult.setMessage("短信验证码超时");
//				 log.debug("用户登录，结果=responseResult:"+responseResult);
//				 return responseResult;
//			}*/
//		}
//		// 是否锁定
//		boolean flag = false;
//		// 用户登录
//		try {
//			// 1、 封装用户名和密码到token令牌对象 [支持记住我]
//			AuthenticationToken token = new UsernamePasswordToken(
//					user.getMobile(), DigestUtils.md5Hex(user.getPassword()),
//					rememberMe);
//
//			log.info("获取token中的加密密码：" + ((UsernamePasswordToken) token).getPassword());
//			// 2、 Subject调用login
//			Subject subject = SecurityUtils.getSubject();
//			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
//			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
//			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
//			log.info("用户登录，用户验证开始！user=" + user.getMobile());
//			subject.login(token);
//			responseResult.setCode(IStatusMessage.SystemStatus.SUCCESS
//					.getCode());
//			log.info("用户登录，用户验证通过！user=" + user.getMobile());
//		} catch (UnknownAccountException uae) {
//			log.error("用户登录，用户验证未通过：未知用户！user=" + user.getMobile(), uae);
//			responseResult.setMessage("该用户不存在，请您联系管理员");
//		} catch (IncorrectCredentialsException ice) {
//			// 获取输错次数
//			log.error("用户登录，用户验证未通过：错误的凭证，密码输入错误！user=" + user.getMobile(),
//					ice);
//			responseResult.setMessage("用户名或密码不正确");
//		} catch (LockedAccountException lae) {
//			log.error("用户登录，用户验证未通过：账户已锁定！user=" + user.getMobile(), lae);
//			responseResult.setMessage("账户已锁定");
//		} catch (ExcessiveAttemptsException eae) {
//			log.error(
//					"用户登录，用户验证未通过：错误次数大于5次,账户已锁定！user=.getMobile()" + user, eae);
//			responseResult.setMessage("用户名或密码错误次数大于5次,账户已锁定，2分钟后可再次登录或联系管理员解锁");
//			// 这里结合了，另一种密码输错限制的实现，基于redis或mysql的实现；也可以直接使用RetryLimitHashedCredentialsMatcher限制5次
//			flag = true;
//		} /*catch (DisabledAccountException sae){
//		 log.error("用户登录，用户验证未通过：帐号已经禁止登录！user=" +
//		 user.getMobile(),sae);
//		 responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
//		 responseResult.setMessage("帐号已经禁止登录");
//		}*/catch (AuthenticationException ae) {
//			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
//			log.error("用户登录，用户验证未通过：认证异常，异常信息如下！user=" + user.getMobile(),
//					ae);
//			responseResult.setMessage("用户名或密码不正确");
//		} catch (Exception e) {
//			log.error("用户登录，用户验证未通过：操作异常，异常信息如下！user=" + user.getMobile(), e);
//			responseResult.setMessage("用户登录失败，请您稍后再试");
//		}
//		if (flag) {
//			// 已经输错6次了，将进行锁定！【也可以使用redis记录密码输错次数，然后进行锁定//TODO】
//			int num = this.userService.setUserLockNum(existUser.getId(), 1);
//			if (num < 1) {
//				log.info("用户登录，用户名或密码错误次数大于5次,账户锁定失败！user="
//						+ user.getMobile());
//			}
//		}
//		log.debug("用户登录，user=" + user.getMobile() + ",登录结果=responseResult:"
//				+ responseResult);
//		return responseResult;
//	}
//
//	/**
//	 * 发送短信验证码
//	 * @param mobile
//	 * @param picCode
//	 * @return
//	 */
//	@RequestMapping(value = "sendMessage", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseResult sendMessage(@RequestParam("mobile") String mobile,
//			@RequestParam("picCode") String picCode) {
//		log.debug("发送短信验证码！mobile:" + mobile + ",picCode=" + picCode);
//		ResponseResult responseResult = new ResponseResult();
//		try {
//			if (!ValidateUtil.isMobilephone(mobile)) {
//				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
//						.getCode());
//				responseResult.setMessage("手机号格式有误，请您重新填写");
//				log.debug("发送短信验证码，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			if (!ValidateUtil.isPicCode(picCode)) {
//				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
//						.getCode());
//				responseResult.setMessage("图片验证码有误，请您重新填写");
//				log.debug("发送短信验证码，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			// 判断用户是否登录
//			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
//			if (null == existUser) {
//				responseResult.setCode(IStatusMessage.SystemStatus.NO_LOGIN
//						.getCode());
//				responseResult.setMessage("您未登录或登录超时，请您重新登录后再试");
//				log.debug("发送短信验证码，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			// 发送短信验证码
//			// String msg=userService.sendMessage(existUser.getId(),mobile);
//			String msg = "ok";
//			if (msg != "ok") {
//				responseResult.setCode(IStatusMessage.SystemStatus.ERROR
//						.getCode());
//				responseResult.setMessage(msg == "no" ? "发送验证码失败，请您稍后再试" : msg);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
//			responseResult.setMessage("发送短信验证码失败，请您稍后再试");
//			log.error("发送短信验证码异常！", e);
//		}
//		log.debug("发送短信验证码，结果=responseResult:" + responseResult);
//		return responseResult;
//	}
//
//	/**
//	 * 修改密码之确认手机号
//	 * @param mobile
//	 * @param picCode
//	 * @return
//	 */
//	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseResult updatePwd(@RequestParam("mobile") String mobile,
//			@RequestParam("picCode") String picCode,
//			@RequestParam("mobileCode") String mobileCode) {
//		log.debug("修改密码之确认手机号！mobile:" + mobile + ",picCode=" + picCode
//				+ ",mobileCode=" + mobileCode);
//		ResponseResult responseResult = new ResponseResult();
//		responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
//				.getCode());
//		try {
//			if (!ValidateUtil.isMobilephone(mobile)) {
//				responseResult.setMessage("手机号格式有误，请您重新填写");
//				log.debug("修改密码之确认手机号，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			if (!ValidateUtil.isPicCode(picCode)) {
//				responseResult.setMessage("图片验证码有误，请您重新填写");
//				log.debug("发修改密码之确认手机号，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			if (!ValidateUtil.isCode(mobileCode)) {
//				responseResult.setMessage("短信验证码有误，请您重新填写");
//				log.debug("发修改密码之确认手机号，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			// 判断用户是否登录
//			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
//			if (null == existUser) {
//				responseResult.setMessage("您未登录或登录超时，请您重新登录后再试");
//				log.debug("修改密码之确认手机号，结果=responseResult:" + responseResult);
//				return responseResult;
//			} else {
//				// 校验验证码
//				/*if(!existUser.getMcode().equals(user.getSmsCode())){ //不等
//				 responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
//				 responseResult.setMessage("短信验证码输入有误");
//				 log.debug("用户登录，结果=responseResult:"+responseResult);
//				 return responseResult;
//				} //1分钟
//				long beginTime =existUser.getSendTime().getTime();
//				long endTime = new Date().getTime();
//				if(((endTime-beginTime)-60000>0)){
//					 responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
//					 responseResult.setMessage("短信验证码超时");
//					 log.debug("用户登录，结果=responseResult:"+responseResult);
//					 return responseResult;
//				}*/
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
//			responseResult.setMessage("操作失败，请您稍后再试");
//			log.error("修改密码之确认手机号异常！", e);
//			return responseResult;
//		}
//		responseResult.setCode(IStatusMessage.SystemStatus.SUCCESS.getCode());
//		responseResult.setMessage("SUCCESS");
//		log.debug("修改密码之确认手机号，结果=responseResult:" + responseResult);
//		return responseResult;
//	}
//
//	/**
//	 * 修改密码
//	 * @param pwd
//	 * @param isPwd
//	 * @return
//	 */
//	@RequestMapping(value = "setPwd", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseResult setPwd(@RequestParam("pwd") String pwd,
//			@RequestParam("isPwd") String isPwd) {
//		log.debug("修改密码！pwd:" + pwd + ",isPwd=" + isPwd);
//		ResponseResult responseResult = new ResponseResult();
//		try {
//			if (!ValidateUtil.isSimplePassword(pwd)
//					|| !ValidateUtil.isSimplePassword(isPwd)) {
//				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
//						.getCode());
//				responseResult.setMessage("密码格式有误，请您重新填写");
//				log.debug("修改密码，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			if (!pwd.equals(isPwd)) {
//				responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR
//						.getCode());
//				responseResult.setMessage("两次密码输入不一致，请您重新填写");
//				log.debug("发修改密码，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			// 判断用户是否登录
//			User existUser = (User) SecurityUtils.getSubject().getPrincipal();
//			if (null == existUser) {
//				responseResult.setCode(IStatusMessage.SystemStatus.NO_LOGIN
//						.getCode());
//				responseResult.setMessage("您未登录或登录超时，请您重新登录后再试");
//				log.debug("修改密码，结果=responseResult:" + responseResult);
//				return responseResult;
//			}
//			// 修改密码
//			int num = this.userService.updatePwd(existUser.getId(),
//					DigestUtils.md5Hex(pwd));
//			if (num != 1) {
//				responseResult.setCode(IStatusMessage.SystemStatus.ERROR
//						.getCode());
//				responseResult.setMessage("操作失败，请您稍后再试");
//				log.debug("修改密码失败，已经,或该用户被删除！结果=responseResult:"
//						+ responseResult);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
//			responseResult.setMessage("操作失败，请您稍后再试");
//			log.error("修改密码异常！", e);
//		}
//		log.debug("修改密码，结果=responseResult:" + responseResult);
//		return responseResult;
//	}
//
//	/**
//	 * @描述：校验请求参数
//	 * @param obj
//	 * @param response
//	 * @return
//	 */
//	protected boolean validatorRequestParam(Object obj, ResponseResult response) {
//		boolean flag = false;
//		Validator validator = new Validator();
//		List<ConstraintViolation> ret = validator.validate(obj);
//		if (ret.size() > 0) {
//			// 校验参数有误
//			response.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
//			response.setMessage(ret.get(0).getMessageTemplate());
//		} else {
//			flag = true;
//		}
//		return flag;
//	}
	@ApiOperation(value="获取当前用户的关联用户")
	@GetMapping("/searchRelation")
	public RestResponseBo searchRelationUsers(@RequestParam("userId") Integer userId, @RequestParam("relationType") Integer relationType){
		List<User> list = userService.searchRelations(userId, relationType);
		return RestResponseBo.ok(list);
	}
	@ApiOperation(value="更新用户的体重信息")
	@PutMapping("/user/updateWeight")
	public RestResponseBo updateWeight(@RequestParam("userId") Integer userId,
			@RequestParam("weight") Integer weight){
		int value = userService.UpdateWeight(userId, weight);
		return RestResponseBo.ok(value);
	}
}
