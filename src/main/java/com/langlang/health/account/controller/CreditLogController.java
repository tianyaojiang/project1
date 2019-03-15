package com.langlang.health.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langlang.health.account.entity.CreditLog;
import com.langlang.health.account.entity.CreditLogDTO;
import com.langlang.health.account.service.CreditLogService;
import com.langlang.health.common.entity.PageBO;
import com.langlang.health.common.entity.RestResponseBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags="我的积分")
@RestController
@RequestMapping("/creditLog")
public class CreditLogController {

	@Autowired
	private CreditLogService creditLogService;
	
	@ApiOperation(value="新增积分", notes = "")
	@PostMapping
    public RestResponseBo<?> add(@RequestBody CreditLogDTO cl) throws Exception{
		boolean res = creditLogService.saveCreditLog(cl.getUserId(), cl.getCreditType(), cl.getCredit());
		if(res){
			return RestResponseBo.ok();
		}
		return RestResponseBo.fail();
	}
	
	@ApiOperation(value="我的积分", notes = "通过当前登录用户ID查询我的积分列表")
	@ApiImplicitParams({
	    @ApiImplicitParam(name="userId", value="用户ID", required=true, paramType="query"),
	    @ApiImplicitParam(name="pageNum", value="页码", required=false, paramType="query", defaultValue="1"),
	    @ApiImplicitParam(name="pageSize", value="页面大小", required=false, paramType="query", defaultValue="10")
	})
	@GetMapping
    public RestResponseBo<?> get(@RequestParam(value="userId", required = true)int userId, @RequestParam(value="pageNum", required = false, defaultValue = "1") int pageNum, @RequestParam(value="pageSize", required = false, defaultValue = "10") int pageSize) throws Exception{
		CreditLog creditLog = new CreditLog();
		creditLog.setUserId(userId);
		PageBO page = creditLogService.getCreditLog(creditLog, pageNum, pageSize);
		return RestResponseBo.ok(page);
	}
	
	@ApiOperation(value="获取当前用户的积分")
	@RequestMapping("/:userId")
	public RestResponseBo<?> getCreditLog(@PathVariable Integer userId){
		return null;
	}
	
}
