package com.langlang.health.mine.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.langlang.health.pay.entity.Address;

@Mapper
public interface AddressMapper {
	List<Address> getAddressByUserId(Integer userId);
	Boolean addAddress(Map<String, Object> params);
	Boolean deleteAddress(Integer addressId);
	Boolean updateAddress(@Param("address")Address address,@Param("updateTime")Date updateTime);
}
