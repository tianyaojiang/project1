package com.langlang.health.pay.mapper;

import com.langlang.health.pay.entity.*;



import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authc.Account;

import java.util.List;
import java.util.Map;

/**
 * Created by tyj on 2019/01/08.
 */
@Mapper
public interface ProductMapper {


    /** 查找code */
    public List<Code> getCode(Code code) ;

    /** 查找item */
    public List<Item> getItemList(Item item) ;


    public List<Electrical> getElectrical(Electrical electrical) ;

    public Electrical getElectricalById(Electrical electrical) ;
    @SuppressWarnings("unchecked")
    public List<BuyRecord> getBuyRecordList(Account account) ;

    public List<Address> getAddressList(Address address) ;

    public List<Address> getSelAddresslist(Address address) ;

    public boolean addAddress(Address address) ;

    public boolean updateAddress(Address address) ;

    public boolean deleteAddress(Address address) ;
    public boolean setDefAddress(Address address) ;

    public boolean setSelectAddress(Address address) ;

    public boolean addBuyAccount(BuyAccount buyAccount) ;

    public boolean deleteBuyAccount(BuyAccount buyAccount) ;

    public List<BuyAccount> getBuyAccountList(BuyAccount buyAccount) ;

    public BuyAccount getBuyAccount(BuyAccount buyAccount) ;
    public List<DoctorInformation> getDoctorInfoList(DoctorInformation doctorInformation) ;

    public List<SzAccountService> getServerList(SzAccountService szAccountService);

    public boolean updateDoctorInformation(DoctorInformation doctorInformation) ;

    public boolean addUserDoctorInfo(UserDoctorInfo userDoctorInfo) ;
    public List<DoctorInformation> getUserDoctorList(UserDoctorInfo userDoctorInfo) ;
    
    //获取健康链接服务包
    public List<Electrical> getHealthLink();
    //获取健康服务包
    public List<SzPackageService> getBuyCardiacResult(@Param("itemId") String itemId,@Param("cateId") String cateId);
    //获取sys_itme
    public List<Item> getItem(); 
    //获取sys_code
    public List<Code> getSysCode() ;
    //获取单个服务包内容
    public List<Map> getHealthServiceDetails(@Param("servId") String servId);
}
