package com.langlang.health.pay.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tyj on 2019/01/10.
 */
@Data
public class DoctorInformation {
    private Integer id; // 主键
    private Integer accountCode; // 医生编号
    private String doctorUserName; // 医生用户名
    private String doctorName; // 医生姓名
    private Integer sex; // 性别
    private String photo; // 医生照片
    private String mobileNumber; // 绑定的手机手机号
    private Integer bankType; // 银行卡类型 1预留(公司取现)2、银行卡 3、支付宝 4、微支付
    private String bankName; // 银行名称
    private String bankProvince; // 银行所在省份
    private String bankCity; // 银行所在市/区
    private String bankArea; // 银行所在县/区
    private String branchName; // 银行支行名称
    private String cardNumber; // 卡号、账号
    private String hospital; // 医院
    private String department; // 科室
    private String departmentPhoneNumber; // 科室电话
    private BigDecimal asingDayAmount; // 用户付费价格(天)(对外)
    private BigDecimal dsingDayAmount; // 医生收取价格(天)(对内)
    private Integer maxCheckNumber; // 最大服务人数
    private Integer currentCheckNumber; // 当前服务人数
    private Integer currentRestCheckNumber; // 剩余服务人数
    private Integer servermode; // 服务模式
    private String servermodeName; // 服务模式中文名
    private Integer doctorLevel; // 医师职称 1、医师 2、主治医师 3、副主任医师 4、主任医师
    private String levelTitle; // 医师职称
    private BigDecimal amount; // 现有朗朗电子货币 OR RMB
    private BigDecimal tempamount; // 提现冻结amount后临时记录服务金额
    private String createUser; // 创建者
    private Date createDate; // 创建时间
    private String updateUser; // 修改者
    private Date updateDate; // 修改时间
    private Integer tranStatus; // 交易状态 1、可申请提现 -2、申请通过复核中 3、资金发放中，资金发放完毕可解锁状态为0
    private Integer enabled; // 状态，启用禁用
    private Integer overviewCheckStatus; // 全览图审核权限 0:无法使用 1:可使用
    private Integer organId; // 所在机构ID
    private Integer dempId; // 所在科室ID
    private Integer isCheck; // 是否做异常审核
    private Integer isMarket; // 是否做市场推广
    private String hospitalAddress; // 医院地址
    private String workTime; // 门诊时间
    private String experience; // 执业经历
    private String good; // 擅长

    private Integer isSelect;// 是否选择
}
