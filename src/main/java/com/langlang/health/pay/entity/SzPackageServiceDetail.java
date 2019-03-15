package com.langlang.health.pay.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by tyj on 2019/01/11.
 */
@Data
public class SzPackageServiceDetail {
    private long id;
    private String servId;//套餐包ID
    private String servName;//服务名称
    private Integer servType;//服务类型
    private String equipment;//包含设备
    private BigDecimal deposit;//设备押金
    private BigDecimal singleAtm;//服务包单价
    private Integer servNum;//服务次数
    private Integer singleServDays;//单次服务天数
    private String kmcode;//康码基因码
}
