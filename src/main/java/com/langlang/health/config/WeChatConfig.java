package com.langlang.health.config;


/**
 * 微信支付配置
 * @ClassName: WeChatConfig 
 * @Description: TODO
 */
public class WeChatConfig {
	/**
	 * 公众账号ID
	 */
	public static final String APPID =  "";
	/**
	 * 商户号
	 */
	public static final String MCH_ID =  "";
	/**
	 * 商户密钥
	 */
	public static final String KEY = "";
	/**
	 * APP和网页支付提交用户端ip, Native支付填调用微信支付API的机器IP, 即：服务器ip地址
	 */
	public static final String SPBILL_CREATE_IP = "127.0.0.1";
	/**
	 * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。（需要配置）
	 */
	public static final String NOTIFY_URL = "";
	/**
	 * 支付方式，取值如下：JSAPI，NATIVE，APP
	 */
	public static final String TRADE_TYPE = "APP";
	/**
	 * 微信支付 - 统一下单地址
	 */
	public static final String PLACEANORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

}
