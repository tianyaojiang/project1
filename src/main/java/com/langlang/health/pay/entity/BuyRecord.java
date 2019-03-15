package com.langlang.health.pay.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by tyj on 2019/01/10.
 */
@Data
public class BuyRecord {
    private Integer paymentID; // 主键
    private String date; // 交易日
    private String time; // 交易时间
    private Integer accountCode; // 用户主键
    private String packageID; // 套餐ID
    private String orderNumber; // 订单号
    private String alpayNumber; // 支付宝流水号(交易号)
    private BigDecimal payamount;// 套餐总价
    private String packageBeginDate; // 套餐生效日期
    private String packageEndDate; // 套餐失效日期
    private Integer tranType; // 交易类型 1、付款 2 退款
    private Integer payType; // 支付类型 1、前台支付 2、支付宝 3、银行卡 4、微支付
    private Integer status; // 交易状态 1、已支付 2、待支付 3、已过期,
    private String createUser; // 创建人
    private String createDate; // 创建日期
    private String subject; // 订单名称
    private String body; // 订单描述

    private String compareTime; // sql查询比较时间
    private String userName;
    private String name;

    private String servPackageName; // 状态吗
    private BigDecimal firstAmt;// 说明
    private BigDecimal amt;// 说明
    private String packageImg;
}
