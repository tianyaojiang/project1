package com.langlang.health.system.service.impl;



import com.langlang.health.system.entity.LogVo;
import com.langlang.health.system.mapper.LogMapper;
import com.langlang.health.system.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/**
 * Created by tyj on 2018/11/27.
 */
@Service
public class LogServiceImpl implements LogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogServiceImpl.class);

    @Resource
    private LogMapper logMapper;

    @Override
    public void insertLog(LogVo logVo) {
        logMapper.insert(logVo);
    }

    @Override
    public void insertLog(String action, String data, String ip, Integer authorId) {
        LogVo logs = new LogVo();
        logs.setAction(action);
        logs.setData(data);
        logs.setIp(ip);
        logs.setUserId(authorId);
        logs.setCreateTime(new Date());
        logMapper.insert(logs);
    }


}
