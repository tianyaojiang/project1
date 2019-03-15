package com.langlang.health.mobile.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.langlang.health.mobile.entity.HealthAppraiseData;
import com.langlang.health.mobile.entity.HealthDangerfilterData;

@Mapper
public interface HealthDangerfilterMapper {

	HealthDangerfilterData getDangerfilter(String appid, String time);

	boolean addDangerfilter(HealthDangerfilterData dangerfilter);

	boolean updateDangerfilter(HealthDangerfilterData dangerfilter);

	
	
}
