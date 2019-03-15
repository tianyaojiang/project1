package com.langlang.health.account.service;

import com.langlang.health.account.entity.CreditLog;
import com.langlang.health.common.entity.PageBO;

public interface CreditLogService {
	/**
	 * 新增积分记录
	 * @param userId
	 * @param type
	 * @param credit
	 * @return
	 */
	boolean saveCreditLog(int userId, String creditType, int credit);

	PageBO getCreditLog(CreditLog creditLog, int pageNum, int pageSize);
	
}
