package com.langlang.health.pay.entity;

import lombok.Data;

/**
 * Created by tyj on 2019/01/10.
 */
@Data
public class Address {
    private Integer id;
    private String userName;
    private String nickName;
    private String gender;
    private String mobile;
    private String area;
    private String address;
    private Integer isDefault;
    private Integer isSelect;
}
