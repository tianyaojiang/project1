package com.langlang.health.account.mapper;

import java.util.List;
import java.util.Map;

public interface SportMapper {

	/**
	 * 运动获取接口
	 * @return
	 */
	List<Map> getSportAll(); 
	
	/**
	 * 运动计划获取接口
	 * @return
	 */
	List<Map> getSportList();
	
	/**
	 * 运动上传接口
	 * @return
	 */
	List<Map> getSportUpload();
}
