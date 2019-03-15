package com.langlang.health.pay.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by tyj on 2019/01/10.
 */
@Data
public class BuyAccount {
    private Integer id;

    private String mainAccount;
    private String relationAccount;

    private String createName;
    private Date createTime;

    private String updateName;
    private Date updateTime;

    private String deleteName;
    private Date deleteTime;

}
