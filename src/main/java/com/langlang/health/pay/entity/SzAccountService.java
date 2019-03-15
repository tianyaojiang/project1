package com.langlang.health.pay.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

/**
 * 用户服务记录总表
 */
@Data
public class SzAccountService {
	
	private Integer id;
	private long plId;//用户购买服务包ID
	private String packageName;//用户购买服务包名称
	private Integer accountCode;//用户accountCode
	private String servId;		//服务ID
	private Integer servType;	//服务类型
	private String servName;	//服务名称
	private Date servBeginTime;	//开始时间
	private Date servEndTime;	//结束时间
	private Integer totalCount;//总次数
	private Integer lastCount;//剩余次数
	private BigDecimal singleAtm;//单次服务价格
	private Integer singleServDays;//单次服务天数
	private Integer doctorId;//服务医生
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String kmcode;//康码基因码
	
	private String type; //服务包类型 06 基因类
	private String code;	//用户条码
	private Integer codestatus;	//0待发采集器，1采集器已发货，2样本已接收，3样本已送检，4报告质检中，5已出报告
	private String orderNumber;	//订单号
	private String logisticCode;	//物流编号
	private String shipperName;	//物流公司名称
	private String userName;//用户名
	private String birthday;//出生日期
	private String nickName;//收件人姓名
	private String gender;//收件人性别
	private String mobile;//收件人手机号
	private String area;//收件人地址
	private String address;//收件人地址
	private String addreeing;//详细地址
	private String servTypeStr;
	private String order_status;//收货状态
	private String createUser;//创建用户
	private Date createDate;//创建时间
	private String paymentID;//订单ID
	private String goods_type;//基因判别收发货状态码
	private String equipment;//是否需要仪器
	private String singleName;//单项服务名称
	

	
}
