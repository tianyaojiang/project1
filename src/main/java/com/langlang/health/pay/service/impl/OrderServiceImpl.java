package com.langlang.health.pay.service.impl;

import com.langlang.health.pay.mapper.OrderMapper;
import com.langlang.health.pay.service.OrderService;
import com.langlang.health.pay.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tyj on 2019/01/08.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Override
    public String getOrderNo (String str){
       Map<Object,Object> params = new HashMap<>();
       params.put(1,str);
       params.put(2,14);
       return orderMapper.getOrderNumber(params);

   }
}
