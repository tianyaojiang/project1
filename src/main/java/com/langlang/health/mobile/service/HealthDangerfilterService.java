package com.langlang.health.mobile.service;

import com.langlang.health.mobile.entity.HealthDangerfilterData;

public interface HealthDangerfilterService {

	HealthDangerfilterData getDangerfilter(String appid, String time);

	boolean addDangerfilter(HealthDangerfilterData dangerfilter);

	boolean updateDangerfilter(HealthDangerfilterData dangerfilter);

}
