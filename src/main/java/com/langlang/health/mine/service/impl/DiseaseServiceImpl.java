package com.langlang.health.mine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.langlang.health.mine.entity.Disease;
import com.langlang.health.mine.entity.DiseaseVo;
import com.langlang.health.mine.entity.DiseaseVo1;
import com.langlang.health.mine.mapper.DiseaseMapper;
import com.langlang.health.mine.service.DiseaseService;

import ch.qos.logback.core.joran.action.Action;

import org.springframework.stereotype.Service;

@Service
public class DiseaseServiceImpl implements DiseaseService{

	@Autowired
	private DiseaseMapper diseaseMapper;

	@Override
	public List<DiseaseVo1> searchDiseaseByUserId(Integer userId) {
		List<DiseaseVo1> diseaseVos = new ArrayList<>();
		List<Disease> diseases = diseaseMapper.selectAllDisType(userId);
		diseases.forEach(disease ->{
			diseaseVos.add(convertToDiseaseVo(disease));
		});
		return diseaseVos;
	}

	@Override
	public boolean deleteDisease(Integer userId) {
		int value;
		boolean result = false;
		try {
			value = diseaseMapper.deleteDisease(userId);
			result = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<DiseaseVo1> selectDisease(Integer userId, Integer disType) {
		List<DiseaseVo1> result = new ArrayList<>();
		Map<String, Object> maps = new HashMap<>();
		maps.put("userId", userId);
		maps.put("disType", disType);
		List<Disease> diseases = diseaseMapper.selectDisease(maps);
		diseases.forEach(o->{
			result.add(convertToDiseaseVo(o));
		});
		return result;
	}
	
	public DiseaseVo1 convertToDiseaseVo(Disease disease){
		DiseaseVo1 diseaseVo = new DiseaseVo1();
		diseaseVo.setCode(disease.getCode());
		diseaseVo.setUserId(disease.getUserId());
		diseaseVo.setDisType(disease.getDisType());
		diseaseVo.setCreateTime(disease.getUpdateTime());
		diseaseVo.setDianosticTime(disease.getDianosticTime());
		diseaseVo.setPatients(disease.getPatients());
		diseaseVo.setPatients(disease.getPatients());
		diseaseVo.setValue(disease.getValue());
		return diseaseVo;
	}
	
	public static List<Disease> convertDiseases(DiseaseVo diseaseVo){
		List<Disease> diseases = new ArrayList<>();
		diseaseVo.getDiseaseDetails().forEach(action ->{
			Disease disease = new Disease();
			disease.setCode(diseaseVo.getCode());
			disease.setUserId(diseaseVo.getUserId());
			disease.setDisType(diseaseVo.getDisType());
			disease.setValue(action.getDisease());
			disease.setPatients(action.getPatient());
			disease.setDianosticTime(action.getDianosticTime());
		});
		return diseases;
	}
	
	private Boolean insertResult = true;
	
	@Override
	public boolean insertDisease(DiseaseVo diseaseVo) {
		List<Disease> params = convertDiseases(diseaseVo);
		params.forEach(action ->{
			try {
				int value = diseaseMapper.insertDisease(action);
				if (value == 0) {
					insertResult = false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		// TODO Auto-generated method stub
		return insertResult;
	}

	@Override
	public boolean updateDisease(DiseaseVo diseaseVo) {
		try {
			diseaseMapper.deleteDisease(diseaseVo.getUserId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertDisease(diseaseVo);
	}

	@Override
	public boolean insertBirthDiseaseHis(Disease disease){
		boolean result = true;
		int value = 0;
		try {
			value = diseaseMapper.insertDisease(disease);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(value == 0){
			result = false;
		}
		return result;
	}
}
