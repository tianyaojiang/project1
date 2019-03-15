package com.langlang.health.mine.service;

import java.util.List;
import com.langlang.health.pay.entity.Address;

public interface AddressService {
	List<Address> getAddressByUserId(Integer userId);
	Boolean addAddress(Integer userId, String area, String address);
	Boolean deleteAddress(Integer addressId);
	Boolean updateAddress(Integer userId, String area, String address);
}
