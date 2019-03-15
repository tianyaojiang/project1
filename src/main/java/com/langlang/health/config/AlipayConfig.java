package com.langlang.health.config;


/** 
 * 支付宝支付配置
 * @ClassName: AlipayConfig 
 * @Description: TODO
 */
public class AlipayConfig {
	/**
	 * appID
	 */
	public static String app_id = "";
	/**
	 * 应用私钥 
	 */
	public static String merchant_private_key =  "";
	/**
	 *  应用公钥，用来加签 
	 */
	public static String alipay_public_key =  "";
	/**
	 * 支付宝公钥，用来验签 
	 */
	public static String public_key = "";
	/**
	 * 通知结果回调 
	 */
	public static String notify_url = "";
	/**
	 * 返回结果回调
	 */
	public static String return_url = "";
	/**
	 * 签名类型
	 */
	public static String sign_type = "RSA2";
	/**
	 * 编码 
	 */
	public static String charset = "utf-8";
	/**
	 * 支付接口
	 */
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}
