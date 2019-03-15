package com.langlang.health.pay.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by tyj on 2019/01/11.
 */
@Data
public class SzAccountPackageLog {
    private long id;
    private Integer accountCode;
    private Integer paymentId; // 是否首次购买 1是 0否
    private Integer isFirstBuy; // 是否首次购买 1是 0否
    private String servId;// 服务ID
    private String servName;// 服务名称
    private Integer servTime;// 服务有效期（天）
    private BigDecimal devsRentAmt;// 设备押金
    private BigDecimal servAmt;// 服务包单价
    private BigDecimal servTalAmt;// 用户最后支付的总金额
    private Integer buyServNum;// 购买的服务数量
    private String tEcgDevNo;// 贴片心电设备号（首次购买有值）
    private String kEcgDevNo;// 卡片心电电设备号（首次购买有值）
    private Integer doctorId;// 服务医生ID
    private String platEmpId;// 服务医生用户名
    private String platEmpName;// 服务医生姓名
    private String createBy;
    private Date createTime;

    private List itemList;// 套餐包包含的项目
    private Date beginDate;// 套餐开始日期
    private Date endDate;// 套餐结束日期
    private String type;

}
