package com.langlang.health.account.entity;

import java.util.Date;

import lombok.Data;
/**
 * 血氧
 * @ClassName: BloodOxygen 
 * @Description: TODO
 * @author: Daxiang
 * @date: 2019年1月7日 下午2:17:05
 */
@Data
public class BloodOxygen {

	private Integer id;
	private Integer userId;
	private Date date;
	private Date time;
	private Integer value;
	private Integer heart;
	private Date createTime;
	
}
