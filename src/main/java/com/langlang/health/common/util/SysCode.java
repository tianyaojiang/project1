package com.langlang.health.common.util;

public enum SysCode {
	XD01("XD01", "服务包类型", 1),XD02("XD02", "服务包基因检测状态", 2),XD03("XD01", "服务包类型", 1),XD04("XD01", "服务包类型", 1),XD05("XD01", "服务包类型", 1),XD06("XD01", "服务包类型", 1),XD07("XD01", "服务包类型", 1),XD08("XD01", "服务包类型", 1),XD09("XD01", "服务包类型", 1);
    // 成员变量  
    private String name;  
    private String code; 
    private int index;
    // 构造方法  
    private SysCode(String code, String name, int index) {  
        this.code = code;
        this.name = name;
        this.index = index;
    }  
    //覆盖方法  
    @Override  
    public String toString() {  
        return this.index+"_" + this.code + "_" + this.name;  
    }  
}
