package com.langlang.health.mine.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.mine.entity.Message;
import com.langlang.health.mine.service.MessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="留言反馈")
@RestController
@RequestMapping("/messages")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@ApiOperation(value="获取所有的留言")
	@GetMapping
	public RestResponseBo<?> searchAll(){
		List<Message> list = messageService.searchAll();
		RestResponseBo<Message> result = new RestResponseBo<>();
		return result.ok(list);
	}
	
	@ApiOperation(value="创建一个留言")
	@RequestMapping(value="/create",method=RequestMethod.POST )
	public RestResponseBo<?> insertMessage(@RequestParam("message") String message, @RequestParam("createId") Integer createId, @RequestParam("updateId") Integer updateId){
		RestResponseBo<Boolean> result = new RestResponseBo<>();
		boolean value = messageService.insertMessage(message, createId, updateId);
		if(value){
			result.ok(value);
		}else{
			result.fail();
		}
		return result;
	}
	
	@ApiOperation(value="更新留言")
//	@PutMapping("/update")
	@RequestMapping(value="/update",method=RequestMethod.PUT )
	public RestResponseBo<?> updateMessage(@RequestParam("message") String message, @RequestParam("updateId") Integer updateId){
		RestResponseBo<Boolean> result = new RestResponseBo<>();
		boolean value = messageService.updateMessage(message, updateId);
		if(value){
			result.ok(value);
		}else{
			result.fail();
		}
		return result;
	}
	
	@ApiOperation(value="删除留言")
//	@DeleteMapping("/delete/:id")
//	@RequestMapping(value="/delete/:id",method=RequestMethod.DELETE )
	@DeleteMapping("/delete/{id}")
	public RestResponseBo<?> deleteMessage(@PathVariable("id") Integer id){
		RestResponseBo<Boolean> result = new RestResponseBo<>();
		boolean value = messageService.deleteMessage(id);
		if(value){
			result.ok(value);
		}else{
			result.fail();
		}
		return result;
	}
}
