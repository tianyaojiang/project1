package com.langlang.health.pay.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tyj on 2019/01/10.
 */

@Data
public class Electrical {
    private Integer id;
    private String servId;
    private String servPackageName; // 状态吗
    private BigDecimal firstAmt;// 说明
    private BigDecimal amt;// 说明
    private String content;
    private String packageImg;
    private String about;
    private String remark;
    private String aboutDetail;
    private String serverContent;
    private String imageUrl;

    private List<BuyAccount> buyAccounts;
    private String userName;
    private List<Address> addrList;
    private Integer isDefault;
    private Integer isDelete;
    private BigDecimal charge;// 运费
    private Integer isEquipment;// 是否 需要设备

    private List<SzPackageServiceDetail> packageItemList;
    private List<DoctorInformation> doctorList;

    private String cateId;
    private String itemId;
    private String itemName;
    private Integer isLocked;
    private String type;
    private Integer sort;
}
