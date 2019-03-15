
package com.langlang.health.account.mapper;

import java.util.List;
import java.util.Map;

/**
* @author htd
* @version 创建时间：2019年1月8日 下午3:56:27
*/

public interface DietaryMapper {

	/**
	 * 膳食计划获取接口
	 * @return
	 */
	List<Map> getDietaryPlan();
	
	/**
	 * 今日（历史）膳食获取
	 * @return
	 */
	List<Map> getDietaryHistory();
	/**
	 * 今日膳食上传接口
	 * @return
	 */
	List<Map> getDietaryUpload();
	
}
