package com.langlang.health.pay.controller;


import com.alibaba.fastjson.JSONObject;
import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.pay.entity.*;
import com.langlang.health.pay.service.ProductService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * Created by tyj on 2018/08/13.
 */
@Api(tags = "服务包相关")
@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    public static final String XD01 = "XD01";

    @Autowired
    ProductService productService;


    /**
     * 分页查询产品列表
     *
     * @return ok/fail
     */
    @RequestMapping(value = "/getProducts", method = RequestMethod.POST)
    //@RequiresPermissions(value = "product:view")
    public RestResponseBo getProducts(HttpServletRequest request,
                                      @RequestParam String userName, @RequestParam String servId,
                                      @RequestParam String itemId, @RequestParam String cateId
                                      ) {
        HashMap<String, Object> jsonMap = new HashMap<>();
        Item item = new Item();
        List<Item> itemList = productService.getItemList(item);
        Code code = new Code();
        code.setCode(XD01);
        List<Code> menuList = productService.getCodes(code);
        if (cateId != null) {
            cateId = "00".equals(cateId) ? "" : cateId;
        }
        if (itemId != null) {
            itemId = "1".equals(itemId) ? "" : itemId;
        }
        if ("6".equals(itemId)) {
            itemId = "2";
        }
        Electrical product = new Electrical();
        product.setUserName(userName);
        product.setServId(servId);
        product.setIsDelete(0);
        product.setItemId(itemId);
        product.setCateId(cateId);
        jsonMap = new HashMap<>();
        List<Electrical> eleList = productService.getElectricalList(product);
//        jsonMap.put("bannerList", bannerList);
        jsonMap.put("itemList", itemList);
        jsonMap.put("menuList", menuList);
        jsonMap.put("data", eleList);
        jsonMap.put("success", true);
        return RestResponseBo.ok(jsonMap, 1);
    }

    /** 获取服务包详细信息 */
    @RequestMapping(value = "/getPackegeDetail", method = RequestMethod.POST)
    public RestResponseBo getPackegeDetail(HttpServletRequest request, @RequestParam String servId) {
 
        try {
            log.info("*************************获取心电服务包列表开始******************************************");
            HashMap<String, Object> jsonMap = new HashMap<>();
            String message ;
            if (StringUtils.isEmpty(servId)) {
                log.error("*************************请求参数有误！服务包id不存在******************************************");
                message = "服务包id不存在";
                return RestResponseBo.fail(message);
            }
//            List<SzPackageServiceDetail> serverList = szPackageServiceDetailService
//                    .getSzPackageServiceDetailByServId(servId);
//            if (serverList == null) {
//                serverList = new ArrayList<>();
//            }
            jsonMap = new HashMap<>();
            Electrical electrical = new Electrical();
            electrical.setServId(servId);
            Electrical electrical1 = productService.getElectricalById(electrical);
            if (electrical1 == null) {
                log.error("*************************请求参数有误！未找到服务包******************************************");
                message = "未找到服务包";
                return RestResponseBo.fail(message);
            }
//            electrical1.setPackageItemList(serverList);
            jsonMap.put("data", electrical1);
            jsonMap.put("success", true);
            log.info("*************************获取心电服务包列表结束******************************************");
           return RestResponseBo.ok(jsonMap, 1);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("*************************获取心电服务包列表异常******************************************");
            return   RestResponseBo.fail(ex.getMessage());
        }
    }

    /** 获取心电服务包详情页面 */
    @RequestMapping(value = "/getElectricalDetail", method = RequestMethod.POST)
    public RestResponseBo getElectricalDetail(HttpServletRequest request, @RequestParam String userName, @RequestParam String servId) {
        try {
            log.info("*************************获取心电服务包详情页面开始******************************************");
            HashMap<String, Object> jsonMap = new HashMap<>();
            String message ;
            if (StringUtils.isEmpty(userName)) {
                log.error("*************************请求参数有误！用户不存在******************************************");
                message = "用户不存在";
                return RestResponseBo.fail(message);
            }
//            this.account = accountService.getAccountByUserName(userName);
//            if (this.account == null) {
//                log.error("*************************用户不存在******************************************");
//                message = "用户不存在";
//                return RestResponseBo.fail(message);
//            }
            if (StringUtils.isEmpty(servId)) {
                log.error("*************************请求参数有误！服务包不存在******************************************");
                message = "服务包不存在";
                return RestResponseBo.fail(message);
            }
            Address address = new Address();
            address.setUserName(userName);
            List<Address> addresses = productService.getSelAddresslist(address);

            BuyAccount buyAccount = new BuyAccount();
            buyAccount.setMainAccount(userName);
            List<BuyAccount> buyAccounts = productService.getBuyAccountList(buyAccount);

            UserDoctorInfo userDoctorInfo = new UserDoctorInfo();
//            userDoctorInfo.setUser_id(this.account.getAccountCode());
            // doctorInfo.setAccountCode(47879);
            List<DoctorInformation> doctorList = productService.getUserDoctorList(userDoctorInfo);
            if (doctorList == null) {
                doctorList = new ArrayList<>();
            }

            Electrical electrical = new Electrical();
            electrical.setServId(servId);
            List<Electrical> eleList = productService.getElectricalList(electrical);
            Electrical electrical1 = new Electrical();
            if (eleList != null) {
                if (eleList.size() > 0) {
                    electrical1 = eleList.get(0);
                    electrical1.setAddrList(addresses);
                    electrical1.setBuyAccounts(buyAccounts);
                    electrical1.setDoctorList(doctorList);
                }
            }

            jsonMap = new HashMap<>();
            jsonMap.put("data", electrical1);
            jsonMap.put("success", true);
            log.info("*************************获取心电服务包详情页面结束******************************************");
           return RestResponseBo.ok(jsonMap, 1);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("*************************获取心电服务包详情页面异常******************************************");
           return   RestResponseBo.fail(ex.getMessage());
        }

    }

    /** 增加购买账户 */
    @RequestMapping(value = "/addBuyAccount", method = RequestMethod.POST)
    public RestResponseBo addBuyAccount(HttpServletRequest request, @RequestParam String userName, @RequestParam String account) {

        try {
            log.info("*************************增加购买账户 开始******************************************");
            HashMap<String, Object> jsonMap = new HashMap<>();
            String message ;

            if (StringUtils.isEmpty(userName)) {
                log.error("*************************请求参数有误！用户不存在******************************************");
                message = "用户不存在";
                return RestResponseBo.fail(message);
            }
//            this.account = accountService.getAccountByUserName(userName);
//            if (this.account == null) {
//                log.error("*************************用户不存在******************************************");
//                message = "登录用户不存在";
//                return RestResponseBo.fail(message);
//            }
//            if (StringUtils.isEmpty(relAccount)) {
//                log.error("*************************请求参数有误！账户不存在******************************************");
//                message = "登录用户不存在";
//               return RestResponseBo.fail(message);
//            }
//
//            if (relAccount.equals(userName)) {
//                message = "不能添加本人账户";
//               return RestResponseBo.fail(message);
//            }

            BuyAccount buyAccount1 = new BuyAccount();
            buyAccount1.setMainAccount(userName);
//            buyAccount1.setRelationAccount(relAccount);
            BuyAccount buyAccount2 = productService.getBuyAccount(buyAccount1);
            if (buyAccount2 != null) {

                message = "添加账户已存在";
               return RestResponseBo.fail(message);
            }
//            account = accountService.getAccountByUserName(relAccount);
            if (account == null) {
                message = "添加账户非法";
                return RestResponseBo.fail(message);
            }

            BuyAccount buyAccount = new BuyAccount();
            buyAccount.setMainAccount(userName);
            //buyAccount.setRelationAccount(relAccount);
            buyAccount.setCreateName(userName);
            buyAccount.setCreateTime(new Date());
            productService.addBuyAccount(buyAccount);
            jsonMap = new HashMap<>();
            jsonMap.put("data", "true");
            jsonMap.put("success", true);

            log.info("*************************增加购买账户 结束******************************************");
            return RestResponseBo.ok(jsonMap, 1);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("*************************增加购买账户 异常******************************************");
            return RestResponseBo.fail(ex.getMessage());
        }

    }

    /** 删除购买账户 */
    @RequestMapping(value = "/deleteBuyAccount", method = RequestMethod.POST)
    public RestResponseBo deleteBuyAccount(HttpServletRequest request, @RequestParam String userName, @RequestParam String aid) {
        try {
            log.info("*************************删除购买账户 开始******************************************");
            HashMap<String, Object> jsonMap = new HashMap<>();
            String message ;
            if (StringUtils.isEmpty(userName)) {
                log.error("*************************请求参数有误！用户不存在******************************************");
                message = "用户不存在";
                return RestResponseBo.fail(message);
            }

            BuyAccount buyAccount = new BuyAccount();
            if (StringUtils.isEmpty(aid)) {
                buyAccount.setMainAccount(userName);
            } else {
                buyAccount.setId(Integer.parseInt(aid));
            }
            buyAccount.setDeleteName(userName);
            buyAccount.setDeleteTime(new Date());
            productService.deleteBuyAccount(buyAccount);
            jsonMap = new HashMap<>();
            jsonMap.put("data", "true");
            jsonMap.put("success", true);
            log.info("*************************删除购买账户 结束******************************************");
            return RestResponseBo.ok(jsonMap, 1);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("*************************删除购买账户 异常******************************************");
            return RestResponseBo.fail(ex.getMessage());
        }

    }




    /********************************************************** 收货地址 **************************************************************/
    /****** 获取用户收货地址 列表 *****/
    @RequestMapping(value = "/getAddress", method = RequestMethod.POST)
    public RestResponseBo getAddress(HttpServletRequest request,  @RequestParam String userName,@RequestParam String aid,@RequestParam String isDefault) {
        log.info("*************************获取用户收货地址 列表开始******************************************");
        try {
            String message;
            Integer def = null;
            if (!StringUtils.isEmpty(isDefault)) {
                def = Integer.parseInt(isDefault);
            }
            if (StringUtils.isEmpty(userName)) {
                log.error("*************************请求参数有误,用户名为空******************************************");
                message= "用户名不存在";
                return RestResponseBo.fail(message);
            }

            Address address = new Address();
            address.setUserName(userName);
            address.setIsDefault(def);
            address.setId(Integer.parseInt(aid));
            Map<String, Object> orderMap = new HashMap<>();
            List<Address> addresses = productService.getAddressList(address);
            orderMap.put("data", addresses);
            orderMap.put("success", true);
            return RestResponseBo.ok(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("************************获取用户收货地址 列表异常******************************************");
            return RestResponseBo.fail(e.getMessage());
        }

    }

    /****** 新增用户收货地址 *****/
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST)
    public RestResponseBo addAddress(HttpServletRequest request,  @RequestParam String userName,@RequestParam String nickName,
                                     @RequestParam String mobile,@RequestParam String gender, @RequestParam String area,@RequestParam String address,@RequestParam String isDefault) {

        log.info("*************************新增用户收货地址开始******************************************");
        String addr = address;
        if (StringUtils.isEmpty(isDefault)) {
            isDefault = "0";
        }
        try {
            String message;
            if (StringUtils.isEmpty(userName)) {
                log.error("*************************请求参数有误,用户名为空******************************************");
                message= "用户名不存在";
                return RestResponseBo.fail(message);
            }
            Address address1 = new Address();
            address1.setUserName(userName);
            address1.setNickName(nickName);
            address1.setMobile(mobile);
            address1.setGender(gender);
            address1.setArea(area);
            address1.setAddress(addr);
            address1.setIsDefault(Integer.parseInt(isDefault));
            Map<String, Object> orderMap = new HashMap<>();
            boolean b = productService.addAddress(address1);
            orderMap.put("data", b);
            orderMap.put("success", true);
            return RestResponseBo.ok(orderMap);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.info("************************新增用户收货地址异常******************************************");
            return RestResponseBo.fail(e.getMessage());
        }

    }

    /****** 更新用户收货地址 *****/
    @RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
    public RestResponseBo updateAddress(HttpServletRequest request,  @RequestParam String userName,@RequestParam String nickName, @RequestParam String aid,
                                        @RequestParam String mobile,@RequestParam String gender, @RequestParam String area,@RequestParam String address) {
        log.info("************************* 更新用户收货地址 开始******************************************");
        try {
            String message;
            if (StringUtils.isEmpty(userName)) {
                log.error("*************************请求参数有误,用户名为空******************************************");
                message= "用户名不存在";
                return RestResponseBo.fail(message);
            }
            if (StringUtils.isEmpty(aid)) {
                log.error("*************************请求参数有误,地址id为空******************************************");
                message= "地址不存在";
                return RestResponseBo.fail(message);
            }
            Address address1 = new Address();
            address1.setId(Integer.parseInt(aid));
            address1.setUserName(userName);
            address1.setNickName(nickName);
            address1.setMobile(mobile);
            address1.setGender(gender);
            address1.setArea(area);
            address1.setAddress(address);
            // address.setIsDefault(Integer.parseInt(isDefault));
            Map<String, Object> orderMap = new HashMap<>();
            boolean b = productService.updateAddress(address1);
            orderMap.put("data", b);
            orderMap.put("success", true);
            return RestResponseBo.ok(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("************************ 更新用户收货地址 异常******************************************");
            return RestResponseBo.fail(e.getMessage());
        }

    }

    /****** 删除用户收货地址 *****/
    @RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
    public RestResponseBo deleteAddress(HttpServletRequest request,  @RequestParam String aid) {

        log.info("*************************删除用户收货地址开始******************************************");
        try {
            String message;
            if (StringUtils.isEmpty(aid)) {
                log.error("*************************请求参数有误,地址id为空******************************************");
                message= "地址不存在";
                return RestResponseBo.fail(message);
            }

            Address address = new Address();
            address.setId(Integer.parseInt(aid));
            Map<String, Object> orderMap = new HashMap<>();
            boolean b = productService.deleteAddress(address);
            orderMap.put("data", b);
            orderMap.put("success", true);
            return RestResponseBo.ok(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("************************删除用户收货地址异常******************************************");
            return RestResponseBo.fail(e.getMessage());
        }

    }

    /****** 设置默认收货地址 *****/
    @RequestMapping(value = "/setDefAddress", method = RequestMethod.POST)
    public RestResponseBo setDefAddress(HttpServletRequest request, @RequestParam String userName, @RequestParam String aid) {
        log.info("*************************设置默认收货地址 开始******************************************");
        try {
            String message;
            if (StringUtils.isEmpty(userName)) {
                log.error("*************************请求参数有误,用户名为空******************************************");
                message= "用户名不存在";
                return RestResponseBo.fail(message);
            }
            if (StringUtils.isEmpty(aid)) {
                log.error("*************************请求参数有误,地址id为空******************************************");
                message= "地址不存在";
                return RestResponseBo.fail(message);
            }
            Address address = new Address();
            address.setUserName(userName);
            address.setId(Integer.parseInt(aid));
            Map<String, Object> orderMap = new HashMap<>();
            productService.setDefaultAddress(address);
            address.setIsDefault(1);
            boolean b = productService.updateAddress(address);
            orderMap.put("data", b);
            orderMap.put("success", true);
            return RestResponseBo.ok(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("************************设置默认收货地址 异常******************************************");
            return RestResponseBo.fail(e.getMessage());
        }

    }

    /****** 设置选中收货地址 *****/
    @RequestMapping(value = "/setSelectAddress", method = RequestMethod.POST)
    public RestResponseBo setSelectAddress(HttpServletRequest request, @RequestParam String userName, @RequestParam String aid) {

        log.info("*************************设置默认收货地址 开始******************************************");

        try {
            String message ;
            if (StringUtils.isEmpty(userName)) {
                log.error("*************************请求参数有误,用户名为空******************************************");
                message= "用户名不存在";
                return RestResponseBo.fail(message);
            }
            if (StringUtils.isEmpty(aid)) {
                log.error("*************************请求参数有误,地址id为空******************************************");
                message= "地址不存在";
                return RestResponseBo.fail(message);
            }
            Address address = new Address();
            address.setUserName(userName);
            address.setId(Integer.parseInt(aid));
            Map<String, Object> orderMap = new HashMap<>();
            productService.setSelectAddress(address);
            address.setIsSelect(1);
            productService.updateAddress(address);
            List<Address> addresses = productService.getSelAddresslist(address);
            orderMap.put("data", addresses);
            orderMap.put("success", true);
            return RestResponseBo.ok(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("************************设置默认收货地址 异常******************************************");
            return RestResponseBo.fail();
        }
    }


    @RequestMapping(value = "/getDoctorList", method = RequestMethod.POST)
    public RestResponseBo getDoctorList(HttpServletRequest request, @RequestParam String doctorId) {

        log.info("*************************获取医生列表开始******************************************");
        try {
            Map<String, Object> orderMap = new HashMap<>();
            DoctorInformation doctorInformation = new DoctorInformation();
            if (!StringUtils.isEmpty(doctorId)) {
                doctorInformation.setAccountCode(Integer.parseInt(doctorId));
            }
            List<DoctorInformation> doctorList = productService.getDoctorInfoList(doctorInformation);
            if (doctorList == null) {
                doctorList = new ArrayList<>();
            }
            orderMap.put("data", doctorList);
            orderMap.put("success", true);
            return RestResponseBo.ok(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("************************设置默认收货地址 异常******************************************");
            return RestResponseBo.fail();
        }

    }

    @RequestMapping(value = "/updateDoctorInfo", method = RequestMethod.POST)
    public RestResponseBo updateDoctorInfo(HttpServletRequest request, @RequestParam String userName, @RequestParam String doctorId) {
        log.info("*************************获取医生列表开始******************************************");

        try {
            String message ;
            /** 判断账户是否存在 */
            if (!StringUtils.isEmpty(userName)) {
//                this.account = accountService.getAccountByUserName(userName);

            } else {
                log.error("*************************请求参数有误！用户名不存在******************************************");
                message= "用户名不存在";
                return RestResponseBo.fail(message);
            }
//            if (this.account == null) {
//                log.error("*************************请求参数有误！用户名不存在******************************************");
//                message= "用户名不存在";
//                return RestResponseBo.fail(message);
//            }

            if (StringUtils.isEmpty(doctorId)) {
                log.error("*************************请求参数有误,用户名为空******************************************");

                message= "医生id不存在";
                return RestResponseBo.fail(message);
            }
            Map<String, Object> orderMap = new HashMap<>();
            UserDoctorInfo userDoctorInfo = new UserDoctorInfo();
//            userDoctorInfo.setUser_id(this.account.getAccountCode());
            userDoctorInfo.setDoctor_id(Integer.parseInt(doctorId));
            userDoctorInfo.setIsSelect(1);
            userDoctorInfo.setCreate_name(userName);
            userDoctorInfo.setCreate_time(new Date());
            boolean b = productService.addUserDoctorInfo(userDoctorInfo);
            orderMap.put("data", b);
            orderMap.put("success", true);
            return RestResponseBo.ok(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("************************设置默认收货地址 异常******************************************");
            return RestResponseBo.fail(e.getMessage());
        }

    }


//    @RequestMapping(value = "/getServiceLogList", method = RequestMethod.POST)
//    public RestResponseBo getServiceLogList(HttpServletRequest request, @RequestParam String userName, @RequestParam String plId, @RequestParam String servType) {
//
//        log.info("*************************获取医生列表开始******************************************");
//        try {
//            String message ;
//            /** 判断账户是否存在 */
//            if (!StringUtils.isEmpty(userName)) {
////                this.account = accountService.getAccountByUserName(userName);
//
//            } else {
//                log.error("*************************请求参数有误！用户名不存在******************************************");
//                message= "用户名不存在";
//                return RestResponseBo.fail(message);
//            }
////            if (this.account == null) {
////                log.error("*************************请求参数有误！用户名不存在******************************************");
////                message= "用户名不存在";
////                return RestResponseBo.fail(message);
////            }
//
//            if (StringUtils.isEmpty(plId)) {
//                log.error("*************************请求参数有误******************************************");
//                message= "plId不存在";
//                return RestResponseBo.fail(message);
//            }
//
//            if (StringUtils.isEmpty(servType)) {
//                log.error("*************************请求参数有误******************************************");
//                message= "servType不存在";
//                return RestResponseBo.fail(message);
//            }
//            Map<String, Object> orderMap = new HashMap<>();
//            SzAccountServiceLog szAccountServiceLog = new SzAccountServiceLog();
//            szAccountServiceLog.setPlId(Integer.parseInt(plId));
//            szAccountServiceLog.setServType(Integer.parseInt(servType));
//            List<SzAccountServiceLog> szLogList = szAccountServiceLogService
//                    .getSzAccountServiceLogList(szAccountServiceLog);
//            int total = szAccountServiceLogService.countAll(szAccountServiceLog);
//            orderMap.put("total", total);
//            orderMap.put("data", szLogList);
//            orderMap.put("success", true);
//            return RestResponseBo.ok(orderMap);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("************************设置默认收货地址 异常******************************************");
//            return RestResponseBo.fail(e.getMessage());
//        }
//        log.info("************************设置默认收货地址结束******************************************");
//    }

    /******************************************************************************************************************/



    /**
     * 设备收货状态
     */
//    @RequestMapping(value = "/geneOrderUpdateEquipment", method = RequestMethod.POST)
//    public RestResponseBo geneOrderUpdateEquipment(HttpServletRequest request, @RequestParam String payID, @RequestParam String orderStatus) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        SzAccountService szAccountService = new SzAccountService();
//        szAccountService.setPaymentID(payID);
//        szAccountService.setOrder_status(orderStatus);
//        long l = szAccountServiceService.orderUpdateEquipment(szAccountService);
//        map.put("data", l);
//        return RestResponseBo.ok(map);
//    }
//
//    /**
//     * 基因  收货状态
//     */
//    @RequestMapping(value = "/geneOrderUpdateCountold", method = RequestMethod.POST)
//    public RestResponseBo geneOrderUpdateCountold(HttpServletRequest request, @RequestParam String payID, @RequestParam String orderStatus) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        SzAccountService szAccountService = new SzAccountService();
//        szAccountService.setPaymentID(payID);
//        szAccountService.setOrder_status(orderStatus);
//        long l = szAccountServiceService.orderUpdateCountold(szAccountService);
//        map.put("data", l);
//        return RestResponseBo.ok(map);
//    }

  /**
   * 健康链接    	
   */
    @PostMapping("/HealthLink")
    public RestResponseBo getHealthLink(){
    	
    	String message = "获取内容失败";
    	try {
    		 Map<String, Object> map = new HashMap<String, Object>();
    		 List<Electrical> healthLink = productService.getHealthLink();
    		 if (healthLink!=null && !healthLink.isEmpty()) {
    			 log.info("----------获取内容成功-----------");
    			 map.put("data", healthLink);
    			 return RestResponseBo.ok(map);
			}
    		 
		} catch (Exception e) {
			e.printStackTrace();
			log.info("-------获取内容异常--------");
		}
		return RestResponseBo.fail(message);
    	
    }
/**
 * 健康服务包
 * @return itemId 推荐字段  cateId 筛查字段
 */
    @PostMapping("/buyCardiacResult")
    public RestResponseBo getBuyCardiacResult(String itemId,String cateId){
    	String message = "获取内容失败";
    	try {
    		System.out.println(itemId+"===="+cateId);
   		 Map<String, Object> map = new HashMap<String, Object>();
   		 List<SzPackageService> buyCardiacResult = productService.getBuyCardiacResult(itemId, cateId);
   		 List<Code> code = productService.getSysCode();
   		 List<Item> item = productService.getItem();
   		 if (buyCardiacResult!=null && !buyCardiacResult.isEmpty()) {
   			 log.info("----------获取内容成功-----------");
   			 map.put("data", buyCardiacResult);
   			 map.put("item", item);
   			 map.put("sysCode", code);
   			 return RestResponseBo.ok(map);
			}
   		 
		} catch (Exception e) {
			e.printStackTrace();
			log.info("-------获取内容异常--------");
		}
		return RestResponseBo.fail(message);
    	
    }

   /**
    * 查询单个服务包内容 
    * @return servId 服务ID
    */
    //@RequestMapping(value="/healthServiceDetails",method=RequestMethod.POST)
    @PostMapping("/healthServiceDetails")
 public RestResponseBo getHealthServiceDetails(String servId){
    	String message = "获取内容失败";
    	try {
    		Map<String, Object> map = new HashMap<String, Object>();
    		List<Map> healthServiceDetails = productService.getHealthServiceDetails(servId);
    		String obj = (String) healthServiceDetails.get(0).get("imageUrl");
    		List<String> list = new ArrayList();
    		String[] split = obj.split("\\,");
    		for (String string : split) {
    			list.add("/static/images/paypackage/"+string+"");
			}
    		System.out.println(list);
    		map.put("img", list);
			//System.out.println(healthServiceDetails.get(0).get("imageUrl"));
    		
    		map.put("data", healthServiceDetails);
    		return RestResponseBo.ok(map);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("-------获取内容异常--------");
		}
    	
    	return RestResponseBo.fail(message);
    	
    }

}
