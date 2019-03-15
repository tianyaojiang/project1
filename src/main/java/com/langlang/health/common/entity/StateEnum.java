package com.langlang.health.common.entity;

/**
 * Created by tyj on 2018/09/03.
 */
public enum StateEnum {
    
    DISABLE(0,"禁用"),ENABLE(1,"启用");

    // 成员变量
    private int key;
    private String value;

    // 构造方法
    StateEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }
    // 普通方法
    public static String getValueForKey(int key) {
        for (StateEnum c : StateEnum.values()) {
            if (c.getKey() == key) {
                return c.value;
            }
        }
        return null;
    }

    //set,get
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
