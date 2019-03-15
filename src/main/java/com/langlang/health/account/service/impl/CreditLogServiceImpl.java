package com.langlang.health.account.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.langlang.health.account.entity.CreditLog;
import com.langlang.health.account.mapper.CreditLogMapper;
import com.langlang.health.account.service.CreditLogService;
import com.langlang.health.common.entity.PageBO;

@Service
public class CreditLogServiceImpl implements CreditLogService {

	@Autowired
	private CreditLogMapper creditLogMapper;

	@Override
	public boolean saveCreditLog(int userId, String creditType, int credit) {
		CreditLog creditLog = new CreditLog();
		creditLog.setUserId(userId);
		creditLog.setCreditType(creditType);
		creditLog.setCredit(credit);
		creditLog.setCreateTime(new Date());
		int res = creditLogMapper.saveCreditLog(creditLog);
		return res>0;
	}

	@Override
	public PageBO getCreditLog(CreditLog creditLog, int page, int limit) {
		PageHelper.startPage(page, limit);
		Page<CreditLog> list = creditLogMapper.getCreditLogList(creditLog);
		return new PageBO(list.size(), list);
	}

}
