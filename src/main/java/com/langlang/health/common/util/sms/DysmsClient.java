package com.langlang.health.common.util.sms;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class DysmsClient {
	
    /**
     * 指定短信签名发送验证码
     * @Method sendCode   
     * @Description 
     * @param sign
     * @param smsTemplate
     * @param mobile
     * @return String
     */
    public static String sendCode(String sign, SmsConfig.SmsTemplate smsTemplate, String mobile){
    	String code = getSMSCode();
    	JSONObject param = new JSONObject();
    	param.put("code", code);
    	if(sendSms(sign, smsTemplate, mobile, param)){
    		return code;
    	}
    	return null;
    }
    /**
     * 使用默认签名发送验证码
     * @Method sendCode   
     * @Description 
     * @param smsTemplate
     * @param mobile
     * @return String
     */
    public static String sendCode(SmsConfig.SmsTemplate smsTemplate, String mobile){
    	return sendCode(SmsConfig.defaultSign, smsTemplate, mobile);
    }
    /**
     * 指定短信签名发送短信
     * @Method sendMsg   
     * @Description 不指定变量数目，变量名为："p"+下标 
     * @param sign
     * @param smsTemplate
     * @param mobile
     * @param params
     * @return boolean
     */
    public static boolean sendMsg(String sign, SmsConfig.SmsTemplate smsTemplate, String mobile, String...params){
    	JSONObject param = new JSONObject();
    	for(int i=0; i<params.length; i++){
    		param.put("p"+(i+1), params[i]);
    	}
    	return sendSms(sign, smsTemplate, mobile, param);
    }
    /**
     * 使用默认签名发送短信
     * @Method sendMsg   
     * @Description 不指定变量数目，变量名为："p"+下标 
     * @param smsTemplate
     * @param mobile
     * @param params
     * @return boolean
     */
    public static boolean sendMsg(SmsConfig.SmsTemplate smsTemplate, String mobile, String...params){
    	return sendMsg(SmsConfig.defaultSign, smsTemplate, mobile, params);
    }
    
    /**
     * 发送短信
     * @Method sendSms   
     * @Description 
     * @param sign
     * @param smsTemplate
     * @param mobile
     * @param param
     * @return boolean
     */
    private static boolean sendSms(String sign, SmsConfig.SmsTemplate smsTemplate, String mobile, JSONObject param){
        //hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse;
		try {
			//可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			//初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", SmsConfig.accessKeyId, SmsConfig.accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", SmsConfig.product, SmsConfig.domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			//组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			//必填:待发送手机号
			request.setPhoneNumbers(mobile);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(sign);
			//必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(smsTemplate.getCode());
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			request.setTemplateParam(param.toJSONString());
			sendSmsResponse = acsClient.getAcsResponse(request);
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				return true;
			}
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
        return false;
    }

    /**
     * 获取6位随机数子
     * @Method getSMSCode   
     * @Description 
     * @return String
     */
    private static String getSMSCode(){
    	int mobile_code = (int)((Math.random()*9+1)*100000);
    	return String.valueOf(mobile_code);
    }
}
