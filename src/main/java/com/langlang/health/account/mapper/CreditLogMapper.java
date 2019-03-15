package com.langlang.health.account.mapper;

import com.github.pagehelper.Page;
import com.langlang.health.account.entity.CreditLog;

public interface CreditLogMapper {

	/**
	 * 新增积分记录
	 * @param creditLog
	 * @return
	 */
	int saveCreditLog(CreditLog creditLog);
	
	Page<CreditLog> getCreditLogList(CreditLog creditLog);
	
}
