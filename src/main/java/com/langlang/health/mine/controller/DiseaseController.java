package com.langlang.health.mine.controller;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.mine.entity.Disease;
import com.langlang.health.mine.entity.DiseaseVo;
import com.langlang.health.mine.entity.DiseaseVo1;
import com.langlang.health.mine.entity.SumaryDisease;
import com.langlang.health.mine.service.DiseaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="疾病史")
@RestController
@RequestMapping("/diseases")
public class DiseaseController {
	@Autowired
	private DiseaseService diseaseService;
	private static Map result;
	
	@ApiOperation(value="获取当前用户的疾病概况")
	@GetMapping("/search/{userId}")
	public RestResponseBo<?> getDiseases(@PathVariable("userId") Integer userId){
		if(null == userId || 0 == userId){
			return RestResponseBo.fail();
		}
		SumaryDisease sumaryDisease = new SumaryDisease();
		List<DiseaseVo1> list = diseaseService.searchDiseaseByUserId(userId);
		list.forEach(dis ->{
			if(1 == dis.getDisType()){
				sumaryDisease.setHasFamilyHis(true);
			}else if(2 == dis.getDisType()){
				sumaryDisease.setHasDiseaseHis(true);
			}else if(3 == dis.getDisType() || 4 == dis.getDisType() || 5 == dis.getDisType()){
				sumaryDisease.setHasAllergyHis(true);;
			}else if(6 == dis.getDisType()){
				sumaryDisease.setHasOperationHis(true);
			}else if(7 == dis.getDisType()){
				sumaryDisease.setHasTraumaHis(true);
			}else if(8 == dis.getDisType()){
				sumaryDisease.setHastransfusionHis(true);
			}
		});
		return RestResponseBo.ok(sumaryDisease);
	}
	
	@ApiOperation(value="获取当前用户的健康史")
	@GetMapping("/searchDiseases")
	public RestResponseBo<?> searchDisease(@RequestParam("userId") Integer userId, @RequestParam("disType") Integer disType){
		List<DiseaseVo1> result = diseaseService.selectDisease(userId, disType);
		return RestResponseBo.ok(result);
	}
	
	@ApiOperation(value="生成用户的健康史")
	@PostMapping("/create")
	public RestResponseBo<?> insertDisease(DiseaseVo diseaseVo){
		boolean result = diseaseService.insertDisease(diseaseVo);
		if(result){
			return RestResponseBo.ok(result);
		}else{
			return RestResponseBo.fail("error");
		}
	}
	
	@ApiOperation(value="更新用户的健康史")
	@PostMapping("/update")
    public RestResponseBo<?> updateDisease(DiseaseVo diseaseVo){
		boolean result = diseaseService.updateDisease(diseaseVo);
		if(result){
			return RestResponseBo.ok(result);
		}else{
			return RestResponseBo.fail("error");
		}
	}

	@ApiOperation(value="删除健康史信息")
	@PostMapping("/delete")
    public RestResponseBo<?> deleteDisease(@RequestParam("id") Integer id){
		boolean result = diseaseService.deleteDisease(id);
		if(result){
			return RestResponseBo.ok(result);
		}else{
			return RestResponseBo.fail("error");
		}
    }
	
	public static Map<String, Object> convertToMap(DiseaseVo diseaseVo){
		Map<String, Object> result = new HashMap<>();
		if(diseaseVo == null){
			return result;
		}
		Class class1;
		try {
			class1 = Class.forName("com.langlang.health.mine.entity.DiseaseVo");
			Field[] fields = class1.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				Object fieldValue = field.get(diseaseVo);
				if(fieldValue != null){
					result.put(field.getName(), (field.get(diseaseVo)).toString());
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	
	@ApiOperation(value="插入生产史")
	@PostMapping("/create/birth")
	public RestResponseBo insertBirthDiseaseHis(Disease disease){
		boolean result = diseaseService.insertBirthDiseaseHis(disease);
		return RestResponseBo.ok(result);
	}
	
	public static SimpleDateFormat simpleDateFormat(){
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	}
}
