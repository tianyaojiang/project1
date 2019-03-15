/**
 * 
 */
package com.langlang.health.account.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.account.mapper.DietaryMapper;
import com.langlang.health.account.service.DietaryService;

/**
* @author htd
* @version 创建时间：2019年1月8日 下午3:54:17
* 类说明
*/
@Service
public class DietaryServiceImpl implements DietaryService {

	@Autowired
	private DietaryMapper dietaryMapper;

	/* (non-Javadoc)
	 * @see com.langlang.health.sport.service.DietaryService#getDietaryPlan()
	 */
	@Override
	public List<Map> getDietaryPlan() {
		List<Map> dietaryPlan = null;
		try {
			dietaryPlan = dietaryMapper.getDietaryPlan();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dietaryPlan;
	}

	/* (non-Javadoc)
	 * @see com.langlang.health.sport.service.DietaryService#getDietaryHistory()
	 */
	@Override
	public List<Map> getDietaryHistory() {
		List<Map> dietaryHistory = null;
		try {
			dietaryHistory = dietaryMapper.getDietaryHistory();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dietaryHistory;
	}

	/* (non-Javadoc)
	 * @see com.langlang.health.sport.service.DietaryService#getDietaryUpload()
	 */
	@Override
	public List<Map> getDietaryUpload() {
		List<Map> dietaryUpload = null;
		try {
		   dietaryUpload = dietaryMapper.getDietaryUpload();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dietaryUpload;
	}
	
}
