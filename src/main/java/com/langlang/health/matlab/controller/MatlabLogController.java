package com.langlang.health.matlab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.matlab.entity.MatlabLog;
import com.langlang.health.matlab.service.MatlabLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="算法记录")
@Controller
@RequestMapping("/matlabLog")
public class MatlabLogController {

	@Autowired
	private MatlabLogService matlabLogService;
	
	@ApiOperation(value="查询卡片式心电计算结果", notes = "通过用户名和文件时间查询心电算法分析状态")
	@PostMapping(value = "/matlabLog")
    public RestResponseBo<?> getCardMatlabLog(@RequestParam("userName") String userName, @RequestParam("fileTime") String fileTime) throws Exception{
		MatlabLog matlabLog = matlabLogService.getCardMatlabLog(userName, fileTime);
		return RestResponseBo.ok(matlabLog);
	}
	
}
