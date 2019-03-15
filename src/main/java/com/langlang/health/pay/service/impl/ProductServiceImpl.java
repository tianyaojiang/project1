package com.langlang.health.pay.service.impl;

import com.langlang.health.pay.entity.*;
import com.langlang.health.pay.mapper.ProductMapper;
import com.langlang.health.pay.service.ProductService;

import org.apache.shiro.authc.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by tyj on 2018/08/15.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    /**
     * 获取Code
     */
    @Override
    public List<Code> getCodes(Code code) {

        return productMapper.getCode(code);

    }

    /**
     * 获取item
     */
    @Override
    public List<Item> getItemList(Item item) {

        return productMapper.getItemList(item);

    }

    @Override
    public List<Electrical> getElectricalList(Electrical electrical) {

        return productMapper.getElectrical(electrical);

    }

    @Override
    public Electrical getElectricalById(Electrical electrical) {

        return productMapper.getElectricalById(electrical);

    }

    @Override
    public List<BuyRecord> getBuyRecordList(Account account) {

        return productMapper.getBuyRecordList(account);

    }

    @Override
    public List<Address> getSelAddresslist(Address address) {

        return productMapper.getSelAddresslist(address);

    }

    @Override
    public List<Address> getAddressList(Address address) {

        return productMapper.getAddressList(address);

    }

    @Override
    public boolean addAddress(Address address) {

        return productMapper.addAddress(address);

    }

    @Override
    public boolean updateAddress(Address address) {

        return productMapper.updateAddress(address);

    }

    @Override
    public boolean deleteAddress(Address address) {

        return productMapper.deleteAddress(address);

    }

    @Override
    public boolean setDefaultAddress(Address address) {

        return productMapper.setDefAddress(address);

    }

    @Override
    public boolean setSelectAddress(Address address) {

        return productMapper.setSelectAddress(address);

    }

    @Override
    public boolean addBuyAccount(BuyAccount buyAccount) {

        return productMapper.addBuyAccount(buyAccount);

    }

    @Override
    public boolean deleteBuyAccount(BuyAccount buyAccount) {

        return productMapper.deleteBuyAccount(buyAccount);

    }

    @Override
    public List<BuyAccount> getBuyAccountList(BuyAccount buyAccount) {

        return productMapper.getBuyAccountList(buyAccount);

    }

    @Override
    public BuyAccount getBuyAccount(BuyAccount buyAccount) {

        return productMapper.getBuyAccount(buyAccount);

    }

    @Override
    public List<DoctorInformation> getDoctorInfoList(DoctorInformation doctorInformation) {


        return productMapper.getDoctorInfoList(doctorInformation);

    }

    @Override
    public boolean updateDoctorInformation(DoctorInformation doctorInformation) {

        return productMapper.updateDoctorInformation(doctorInformation);

    }

    @Override
    public List<SzAccountService> getServerList(SzAccountService szAccountService) {

        return productMapper.getServerList(szAccountService);


    }

    @Override
    public boolean addUserDoctorInfo(UserDoctorInfo userDoctorInfo) {

        return productMapper.addUserDoctorInfo(userDoctorInfo);

    }

    @Override
    public List<DoctorInformation> getUserDoctorList(UserDoctorInfo userDoctorInfo) {

        return productMapper.getUserDoctorList(userDoctorInfo);

    }

	/* (non-Javadoc)
	 * @see com.langlang.health.pay.service.ProductService#getHealthLink()
	 * 获取健康链接服务包
	 */
	@Override
	public List<Electrical> getHealthLink() {
		try {
			List<Electrical> healthLink = productMapper.getHealthLink();
			if(healthLink!=null && !healthLink.isEmpty()){
				return healthLink;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.langlang.health.pay.service.ProductService#getBuyCardiacResult(java.lang.String, java.lang.String)
	 * 获取健康服务包
	 */
	@Override
	public List<SzPackageService> getBuyCardiacResult(String itemId, String cateId) {
		try {
			List<SzPackageService> buyCardiacResult = productMapper.getBuyCardiacResult(itemId, cateId);
			if(buyCardiacResult!=null && !buyCardiacResult.isEmpty()){
				
				return buyCardiacResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.langlang.health.pay.service.ProductService#getItem()
	 */
	@Override
	public List<Item> getItem() {
		List<Item> item = null;
		try {
			item = productMapper.getItem();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	/* (non-Javadoc)
	 * @see com.langlang.health.pay.service.ProductService#getSysCode()
	 */
	@Override
	public List<Code> getSysCode() {
		List<Code> sysCode = null;
		try {
			sysCode = productMapper.getSysCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sysCode;
	}

	/* (non-Javadoc)
	 * @see com.langlang.health.pay.service.ProductService#getHealthServiceDetails(java.lang.String)
	 * 获取单个服务包内容
	 */
	@Override
	public List<Map> getHealthServiceDetails(String servId) {
		try {
			List<Map> healthServiceDetails = productMapper.getHealthServiceDetails(servId);
			if(healthServiceDetails!=null && !healthServiceDetails.isEmpty()){
				
				return healthServiceDetails;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


}
