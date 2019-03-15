package com.langlang.health.account.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.langlang.health.account.mapper.SportMapper;
import com.langlang.health.account.service.SportService;

@Service
public class sportMapperImpl implements SportService {

	@Autowired
	private SportMapper sportMapper;
	
	/* (non-Javadoc)
	 * @see com.langlang.health.sport.service.sportMapper#getSportAll()
	 */
	@Override
	public List<Map> getSportAll() {
		List<Map> sportAll = null;
		try {
			sportAll = sportMapper.getSportAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sportAll;
	}

	/* (non-Javadoc)
	 * @see com.langlang.health.sport.service.sportMapper#getSportList()
	 */
	@Override
	public List<Map> getSportList() {
		List<Map> sportList = null;
		try {
			sportList = sportMapper.getSportList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sportList;
	}

	/* (non-Javadoc)
	 * @see com.langlang.health.sport.service.sportMapper#getSportUpload()
	 * 
	 */
	@Override
	public List<Map> getSportUpload() {
		List<Map> sportUpload = null;
		try {
			sportUpload = sportMapper.getSportUpload();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return sportUpload;
	}

	
}
