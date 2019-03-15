package com.langlang.health.pay.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tyj on 2019/01/11.
 */
@Data
public class Payment {
    private Integer paymentID; // 主键
    private Date date; // 交易日
    private Date time; // 交易时间
    private Integer accountCode; // 用户主键
    private String packageID; // 套餐ID
    private String orderNumber; // 订单号
    private String alpayNumber; // 支付宝流水号(交易号)
    private BigDecimal payamount;// 套餐总价
    private Date packageBeginDate; // 套餐生效日期
    private Date packageEndDate; // 套餐失效日期
    private Integer tranType; // 交易类型 1、付款 2 退款
    private Integer payType; // 支付类型 1、前台支付 2、支付宝 3、银行卡 4、微支付
    private Integer status; // 交易状态 1、待支付 2、已支付 3、已过期,
    private String createUser; // 创建人
    private Date createDate; // 创建日期
    private String subject; // 订单名称
    private String body; // 订单描述

    private Date compareTime; // sql查询比较时间
    private String userName;
    private String name;

    private String buyAccounts; // 购买账户
    private Integer isNeedEquipment;// 是否需要设备
    private BigDecimal cashPledge;// 设备押金
    private Integer isNeedInvoice;// 是否需要发票
    private String invoiceRemark;// 发票备注
    private String distributionMode;// 配送方式
    private BigDecimal shippingFee;// 邮费
    private Integer addreeId; // 地址id

    private Integer amount;// 购买数量
    private String newOrderNumber;// 新订单号

    private Integer isDelete;// 是否删除

    private Integer doctorId;// 医生ID

    private String nickName;
    private String mobile;
    private String area;
    private String address;

    private Integer eqAmount;

    private String orderStatus;// 订单状态
}
