package com.langlang.health.system.entity;

import lombok.Data;

/**
 * Created by tyj on 2018/08/14.
 */
@Data
public class UserSearch {

	private Integer page;

	private Integer limit;

	private String username;

	private String mobile;

	private String insertTimeStart;

	private String insertTimeEnd;


}
