package com.langlang.health.mobile.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.langlang.health.mobile.entity.HealthAppraiseData;

@Mapper
public interface HealthAppraiseMapper {

	HealthAppraiseData getAppraise(HealthAppraiseData healthAppraiseData);

	boolean addAppraise(HealthAppraiseData appraise);

	boolean updateAppraise(HealthAppraiseData appraise);
	
}
