package com.langlang.health.mine.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.langlang.health.mine.mapper.AddressMapper;
import com.langlang.health.mine.service.AddressService;
import com.langlang.health.pay.entity.Address;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressMapper addressMapper;
	
	@Override
	public List<Address> getAddressByUserId(Integer userId) {
		return addressMapper.getAddressByUserId(userId);
	}

	@Override
	public Boolean addAddress(Integer userId, String area, String address) {
		Map<String, Object> params = new HashMap<>();
		return addressMapper.addAddress(params);
	}

	@Override
	public Boolean deleteAddress(Integer addressId) {
		return addressMapper.deleteAddress(addressId);
	}

	@Override
	public Boolean updateAddress(Integer id, String area, String address) {
		Address address2 = new Address();
		address2.setId(id);
		address2.setArea(area);
		address2.setAddress(address);
		Date date = new Date();
		return addressMapper.updateAddress(address2, date);
	}
}
