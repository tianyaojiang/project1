package com.langlang.health.account.entity;

import java.util.Date;

import lombok.Data;

/**
 * 血糖
 * @ClassName: BloodSugar 
 * @Description: TODO
 * @author: Daxiang
 * @date: 2019年1月7日 下午2:17:25
 */
@Data
public class BloodSugarDTO {

	private Integer userId;
	private Date time;
	private String name;
	private String type;
	private String value;
	private String result;
	
}
