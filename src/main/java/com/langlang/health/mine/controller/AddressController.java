package com.langlang.health.mine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.mine.service.AddressService;
import com.langlang.health.pay.entity.Address;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="我的地址")
@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	private AddressService addressService;
	
	@ApiOperation(value="获取当前用户的地址")
	@RequestMapping("/search/{userId}")
	public RestResponseBo<?> getAddressByUser(@PathVariable Integer userId){
		List<Address> list = addressService.getAddressByUserId(userId);
		return new RestResponseBo<>().ok(list);
	}
	
	@ApiOperation(value="给当前用户添加地址")
	@PostMapping("/create")
	public RestResponseBo<?> addAddress(@RequestParam("userId") Integer userId, @RequestParam("area") String area,
			@RequestParam("address") String address){
		Boolean result = addressService.addAddress(userId, area, address);
		RestResponseBo<?> responseBo = new RestResponseBo<>();
		if(result){
			responseBo.ok(result);
		}else {
			responseBo.fail();
		}
		return responseBo;
	}
	
	@ApiOperation(value="删除当前用户的地址")
	@DeleteMapping("/delete/{id}")
	public RestResponseBo<?> deleteAddress(@PathVariable("id") Integer id){
		RestResponseBo<?> responseBo = new RestResponseBo<>();
		Boolean result = addressService.deleteAddress(id);
		if(result){
			responseBo.ok(result);
		}else{
			responseBo.fail();
		}
		return responseBo;
	}
	
	@ApiOperation(value="更新当前的用户的地址信息")
	@PutMapping("/update/{id}")
	public RestResponseBo<?> updateAddress(@PathVariable("id") Integer id, @RequestParam("area") String area,
			@RequestParam("address") String address){
		RestResponseBo<?> responseBo = new RestResponseBo<>();
		Boolean result = addressService.updateAddress(id, area, address);
		if(result){
			responseBo.ok(result);
		}else{
			responseBo.fail();
		}
		return responseBo;
	}
}
