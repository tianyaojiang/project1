package com.langlang.health.account.entity;

import java.util.Date;

import lombok.Data;

/**
 * 血压
 * @ClassName: BloodPressureDTO 
 * @Description: TODO
 * @author: Daxiang
 * @date: 2019年1月7日 下午2:17:17
 */
@Data
public class BloodPressureDTO {

	private Integer userId;
	private Date time;
	private Integer upper;
	private Integer floor;
	private Integer heart;
	
}
