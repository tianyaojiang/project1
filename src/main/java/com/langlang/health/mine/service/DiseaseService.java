package com.langlang.health.mine.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.langlang.health.mine.entity.Disease;
import com.langlang.health.mine.entity.DiseaseVo;
import com.langlang.health.mine.entity.DiseaseVo1;

public interface DiseaseService {
	
	/**
	 * 获取用户的病史
	 * @param userId
	 * @return
	 */
	List<DiseaseVo1> searchDiseaseByUserId(Integer userId);
	
    /**
     * 删除用户的疾病史
     * @param id
     * @return
     */
    boolean deleteDisease(Integer userId);
	
	/**
	 * 查询用户的家族疾病史
	 * @param userId
	 * @return
	 */
    List<DiseaseVo1> selectDisease(Integer userId, Integer disType);
	
	/**
	 * 插入用户的疾病史
	 * @param disease
	 * @return
	 */
	boolean insertDisease(DiseaseVo diseaseVo);
	
	/**
	 * 更新用户的疾病史
	 * @param disease
	 * @return
	 */
	boolean updateDisease(DiseaseVo diseaseVo);
	
	boolean insertBirthDiseaseHis(Disease disease);
}
