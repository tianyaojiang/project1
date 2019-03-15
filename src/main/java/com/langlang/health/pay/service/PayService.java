package com.langlang.health.pay.service;

import com.langlang.health.pay.entity.Payment;

import java.util.Map;

/**
 * Created by tyj on 2019/01/11.
 */
public interface PayService {

    public String toPay(Payment payment);

    // 调用微信接口进行统一下单
    public Map<String, String> getWxOrder(Payment payment) throws Exception;

}
