package com.langlang.health.pay.service;


import com.langlang.health.pay.entity.*;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authc.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by tyj on 2018/08/15.
 */
public interface ProductService {
    /** 获取Code */
    public List<Code> getCodes(Code code);

    /** 获取item */
    public List<Item> getItemList(Item item);

    public List<Electrical> getElectricalList(Electrical electrical);

    public Electrical getElectricalById(Electrical electrical);

    public List<BuyRecord> getBuyRecordList(Account account);

    /* 获取地址 */
    public List<Address> getAddressList(Address address);

    public List<Address> getSelAddresslist(Address address);

    /* 新增地址 */
    public boolean addAddress(Address address);

    /* 更新地址 */
    public boolean updateAddress(Address address);

    /* 删除地址 */
    public boolean deleteAddress(Address address);

    /* 设置默认地址 */
    public boolean setDefaultAddress(Address address);

    /* 设置选中地址 */
    public boolean setSelectAddress(Address address);

    /* 获取家属账户列表 */
    public List<BuyAccount> getBuyAccountList(BuyAccount buyAccount);

    /* 获取家属账户 */
    public BuyAccount getBuyAccount(BuyAccount buyAccount);

    /* 新增 家属账户 */
    public boolean addBuyAccount(BuyAccount buyAccount);

    /* 删除 家属账户 */
    public boolean deleteBuyAccount(BuyAccount buyAccount);

    public List<DoctorInformation> getDoctorInfoList(DoctorInformation doctorInformation);

    public boolean updateDoctorInformation(DoctorInformation doctorInformation);

    public List<SzAccountService> getServerList(SzAccountService szAccountService);

    /* 新增用户选医生信息 */
    public boolean addUserDoctorInfo(UserDoctorInfo userDoctorInfo);

    public List<DoctorInformation> getUserDoctorList(UserDoctorInfo userDoctorInfo);

   //获取健康链接服务包
    public List<Electrical> getHealthLink();
    //获取健康服务包
    public List<SzPackageService> getBuyCardiacResult(String itemId,String cateId);
    //获取sys_itme
    public List<Item> getItem(); 
    //获取sys_code
    public List<Code> getSysCode() ;
    //获取单个服务包内容
    public List<Map> getHealthServiceDetails(String servId);


}
