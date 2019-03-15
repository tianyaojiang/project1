package com.langlang.health.pay.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.Map;

/**
 * Created by tyj on 2019/01/08.
 */
@Mapper
public interface OrderMapper {

    @Options(statementType = StatementType.CALLABLE)
    @Select("call getBillNumber(?,?)")
    String getOrderNumber(Map<Object,Object> params);


}
