package com.langlang.health.common.util.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsConfig {

	public static String product;
	public static String domain;
	public static String accessKeyId;
	public static String accessKeySecret;
	public static String defaultSign;
	
	public static String register;
	public static String updatePwd;
	public static String auth;
	public static String login;
	public static String loginError;
	public static String buySuccess;
    
	@Value("${sms.product}")
	public void setProduct(String product) {
		SmsConfig.product = product;
	}
	@Value("${sms.domain}")
	public void setDomain(String domain) {
		SmsConfig.domain = domain;
	}
	@Value("${sms.accessKeyId}")
	public void setAccessKeyId(String accessKeyId) {
		SmsConfig.accessKeyId = accessKeyId;
	}
	@Value("${sms.accessKeySecret}")
	public void setAccessKeySecret(String accessKeySecret) {
		SmsConfig.accessKeySecret = accessKeySecret;
	}
	@Value("${sms.defaultSign}")
	public void setDefaultSign(String defaultSign) {
		SmsConfig.defaultSign = defaultSign;
	}
	
	@Value("${sms.template.register}")
	public void setRegister(String register) {
		SmsConfig.register = register;
	}
	@Value("${sms.template.update_pwd}")
	public void setUpdatePwd(String updatePwd) {
		SmsConfig.updatePwd = updatePwd;
	}
	@Value("${sms.template.auth}")
	public void setAuth(String auth) {
		SmsConfig.auth = auth;
	}
	@Value("${sms.template.login}")
	public void setLogin(String login) {
		SmsConfig.login = login;
	}
	@Value("${sms.template.login_error}")
	public void setLoginError(String loginError) {
		SmsConfig.loginError = loginError;
	}
	@Value("${sms.template.buy_success}")
	public void setBuySuccess(String buySuccess) {
		SmsConfig.buySuccess = buySuccess;
	}

	public static enum SmsTemplate{
		REGISTER(register, "用户注册验证码"),
		UPDATE_PWD(updatePwd, "修改密码验证码"),
		AUTH(auth, "身份验证验证码"),
		LOGIN(login, "登录确认验证码"),
		LOGIN_ERROR(loginError, "登录异常验证码"),
		BUY_SUCCESS(buySuccess, "服务购买成功");

		SmsTemplate(String code, String remark){
			this.code = code;
			this.remark = remark;
		}
		
		private final String code;
		private final String remark;
		public String getCode() {
			return code;
		}
		public String getRemark() {
			return remark;
		}
	}
    
}
