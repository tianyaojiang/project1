package com.langlang.health.pay.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by tyj on 2019/01/10.
 */
@Data
public class UserDoctorInfo {
    private Integer id;
    private Integer user_id;
    private Integer doctor_id;
    private String create_name;
    private Date create_time;
    private String update_name;
    private Date update_time;
    private Integer isDefault;
    private Integer isSelect;
}
