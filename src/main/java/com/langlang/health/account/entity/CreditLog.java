package com.langlang.health.account.entity;

import java.util.Date;

import lombok.Data;

/**
 * 积分
 * @ClassName: Credit 
 * @Description: TODO
 * @author: Daxiang
 * @date: 2019年1月7日 下午2:17:55
 */
@Data
public class CreditLog {

	private Integer id;
	private Integer userId;
	private String creditType;
	private Integer credit;
	private Date createTime;
	
}
