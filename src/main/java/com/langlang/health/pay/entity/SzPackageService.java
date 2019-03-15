package com.langlang.health.pay.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by tyj on 2019/01/11.
 */
@Data
public class SzPackageService {
    private long id;
    private String servId;// 套餐包ID
    private String servPackageName;// 服务名称
    private BigDecimal firstAmt;// 首次购买价格
    private BigDecimal amt;// 单价
    private String content;// 设备押金
    private int servTime;// 服务有效时长（单位天）
    private String packageImg;// 套餐图片
    private String about;// 服务包
    private String remark;// 备注
    private String aboutDetail;// 详情
    private String serverContent;// 服务内容
    private String imageUrl;// 图片
    private String type;
    private String consulting;//健康链接
    private String itemId;// 推荐字段
    private String cateId;//筛查字段
}
