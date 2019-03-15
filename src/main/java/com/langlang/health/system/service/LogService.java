package com.langlang.health.system.service;



import com.langlang.health.system.entity.LogVo;

import java.util.List;

/**
 * Created by tyj on 2018/11/27.
 */
public interface LogService {
    /**
     * 保存操作日志
     *
     * @param logVo
     */
    void insertLog(LogVo logVo);

    /**
     *  保存
     * @param action
     * @param data
     * @param ip
     * @param authorId
     */
    void insertLog(String action, String data, String ip, Integer authorId);


}
