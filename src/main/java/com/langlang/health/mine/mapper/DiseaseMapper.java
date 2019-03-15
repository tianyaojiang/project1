package com.langlang.health.mine.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.langlang.health.mine.entity.Disease;

@Mapper
public interface DiseaseMapper {
	List<Disease> selectAllDisType(Integer userId);
	
	int deleteDisease(Integer userId) throws Exception;
	
	List<Disease> selectDisease(@Param("disease")Map<String, Object> disease);
	
	int insertDisease(@Param("disease")Disease disease) throws Exception;
	
	int insertBirthDisease(@Param("disease")Disease disease) throws Exception;
	
	int updateDisease(@Param("disease")Disease disease) throws Exception;
}
