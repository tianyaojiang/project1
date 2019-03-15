package com.langlang.health.common.util;

import lombok.Data;

/**
 * Created by tyj on 2019/01/08.
 */
public enum LogActions {

    LOGIN("登录后台"),LOGININDEX("登录前台"),REGINDEX("注册前台") , UP_PWD("修改密码"), UP_INFO("修改个人信息"),
    SYS_BACKUP("系统备份"), SYS_SETTING("保存系统设置");

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    LogActions(String action) {
        this.action = action;
    }

}
