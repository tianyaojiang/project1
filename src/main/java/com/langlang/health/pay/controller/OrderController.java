package com.langlang.health.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.wxpay.sdk.WXPayUtil;
import com.langlang.health.common.entity.RestResponseBo;
import com.langlang.health.common.util.sms.DysmsClient;
import com.langlang.health.config.AlipayConfig;
import com.langlang.health.config.WeChatConfig;
import com.langlang.health.pay.service.PayService;
import com.langlang.health.pay.entity.*;
import com.langlang.health.pay.service.OrderService;
import com.langlang.health.pay.service.PaymentService;
import com.langlang.health.pay.service.ProductService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by tyj on 2019/01/08.
 */
@Api(tags = "支付订单相关")
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;
    @Autowired
    PaymentService paymentService;

    @Autowired
    PayService payService;

    /** 支付 */
    @RequestMapping(value = "/toPay", method = RequestMethod.POST)
    public RestResponseBo toPay(
            HttpServletRequest request, @RequestParam String serverId, @RequestParam String payType, 
            @RequestParam String years, @RequestParam String userName, @RequestParam String isNeedEquipment, 
            @RequestParam String cashPledge, @RequestParam String isNeedInvoice, @RequestParam String invoiceRemark,
            @RequestParam String distributionMode, @RequestParam String shippingFee, @RequestParam String addreeId, 
            @RequestParam String buyAccounts, @RequestParam String eqAmount, @RequestParam String doctorId
    ) {

        log.info("*************************支付方法调用开始******************************************");
        HashMap<String, Object> jsonMap = new HashMap<>();
        String message ;
        try {
            log.info("*************************生成支付信息开始******************************************");
            /** 判断账户是否存在 */
            if (StringUtils.isEmpty(userName)) {
                log.error("*************************请求参数有误！用户名不存在******************************************");
                message = "用户不存在";
                return RestResponseBo.fail(message);
            }
//            this.account = accountService.getAccountByUserName(userName);
//            if (this.account == null) {
//                log.error("*************************用户不存在******************************************");
//                message = "用户不存在";
//                return RestResponseBo.fail(message);
//            }
//            if (StringUtils.isEmpty(this.account.getAccountCode())) {
//                log.error("*************************请求参数有误！用户名不存在******************************************");
//                message = "用户不存在";
//                return RestResponseBo.fail(message);
//            }
            /** 判断账服务包是否存在 */
            if (StringUtils.isEmpty(serverId)) {
                log.error("*************************请求参数有误！服务包id不存在******************************************");
                message = "服务包不存在";
                return RestResponseBo.fail(message);
            }

            /** 判断购买数量 */
            if (StringUtils.isEmpty(years)) {
                log.error("*************************请求参数有误！购买数量不能为空******************************************");
                message = "购买数量为空";
                return RestResponseBo.fail(message);
            }

            /** 判断支付类型 */
            if (StringUtils.isEmpty(payType)) {
                log.error("*************************请求参数有误！支付类型不能为空******************************************");
                message = "支付类型为空";
                return RestResponseBo.fail(message);
            }

            if (!"1".equals(payType.trim()) && !"2".equals(payType.trim())) {
                log.error("*************************请求参数有误！支付类型不合法******************************************");
                message = "支付类型不合法！";
                return RestResponseBo.fail(message);
            }

            /** 判断支付类型 */
            if (StringUtils.isEmpty(doctorId)) {
                log.error("*************************请求参数有误！支付类型不能为空******************************************");
                message = "医生ID不能为空！";
                return RestResponseBo.fail(message);
            }

            /** 是否需要设备 */
            Integer isNeedEq = null;
            String orderStatus = "1";
            if (!StringUtils.isEmpty(isNeedEquipment)) {
                isNeedEq = Integer.parseInt(isNeedEquipment);
            }
            /** 设备押金 */
            if (StringUtils.isEmpty(cashPledge)) {
                cashPledge = "0";
            }
            /** 设备数量 */
            if (StringUtils.isEmpty(eqAmount)) {
                eqAmount = "0".equals(cashPledge) ? "0" : "1";
            }
            /** 是否需要发票 */
            Integer isNeedInv = null;
            if (!StringUtils.isEmpty(isNeedInvoice)) {
                isNeedInv = Integer.parseInt(isNeedInvoice);
            }
            /* 运送方式 */
            if (StringUtils.isEmpty(distributionMode)) {
                distributionMode = "快递";
            }
            /* 运费 */
            if (StringUtils.isEmpty(shippingFee)) {
                shippingFee = "0";
            }
            /* 地址 */
            Integer addId = null;
            if (!StringUtils.isEmpty(addreeId)) {
                addId = Integer.parseInt(addreeId);
            }

            /** 查出代买的账户 */
            String[] accounts = null;
            if (!StringUtils.isEmpty(buyAccounts)) {
                accounts = buyAccounts.split(",");
            } else {
                message = "参数错误！";
                return RestResponseBo.fail(message);
            }
            
            
            /* 查询服务包 */
            Electrical electrical = new Electrical();
            electrical.setServId(serverId);
            Electrical electrical1 = productService.getElectricalById(electrical);
            if (electrical1 == null) {
                log.error("*************************请求参数有误！没有找到服务包******************************************");
                message = "服务包不存在！";
                return RestResponseBo.fail(message);
            }
            log.info("*************************生成订单信息开始******************************************");
            // 服务包单价
            BigDecimal price = electrical1.getAmt();
            DecimalFormat df = new DecimalFormat("#.00");
            Double cashPl = Double.parseDouble(cashPledge) * Integer.parseInt(eqAmount);
            BigDecimal cash = new BigDecimal(df.format(cashPl));
            BigDecimal fee = new BigDecimal(df.format(Double.parseDouble(shippingFee)));
            /* 代购账户数 */
            int accNum = 1;
            String buyAcc = userName;
            if (accounts == null || accounts.length == 0) {
                accNum = 1;
//                buyAcc = this.account.getAccountCode().toString();
            } else {
                accNum = accounts.length;
                // 取出代购买账户accountCode
                StringBuffer sb = new StringBuffer();
//                for (String x : accounts) {
//                    Account a = accountService.getAccountByUserName(x);
//                    if (a != null) {
//                        sb.append(a.getAccountCode().toString()).append(",");
//                    }
//                }

                log.info("*************************sb:" + sb + "************************");
                if (sb.length() > 0) {
                    buyAcc = sb.substring(0, sb.length() - 1);
                } else {
                    message = "当前的购买账户非法！";
                    return RestResponseBo.fail(message);
                }

            }

            BigDecimal num = new BigDecimal(accNum);
            // 购买数量
            int year = Integer.parseInt(years);
            BigDecimal bY = new BigDecimal(year);
            /** 合计金额 */
            BigDecimal a = price.multiply(bY);
            log.info("*************************a:" + a + "************************");
            BigDecimal b = a.multiply(num);
            log.info("*************************b:" + b + "************************");
            BigDecimal c = b.add(cash);
            log.info("*************************c:" + c + "************************");
            BigDecimal d = c.add(fee);
            log.info("*************************d:" + d + "************************");
            BigDecimal payamount = d;
            log.info("*************************payamount:" + payamount + "************************");

            log.info("*************************购买账户数 accNum:" + accNum + "************************");
            log.info("*************************购买数量 bY:" + bY + "************************");
            log.info("*************************单价price:" + price + "************************");
            log.info("*************************支付总金额payamount:" + payamount.toString() + "************************");
            log.info("*************************设备押金cash:" + cash + "************************");
            log.info("*************************运费fee:" + fee + "************************");

            Date BeginDate = new Date();
            /** 套餐开始日期 */
            Calendar calendar = Calendar.getInstance();
            /** 套餐开始结束日期 */
            calendar.add(Calendar.YEAR, year);
            calendar.add(Calendar.HOUR_OF_DAY, 0);
            Date packageEndDate = calendar.getTime();
            /** 订单号，朗朗心生成唯一编号 */
            String orderNumber = orderService.getOrderNo("FW");
            int randNum = (int) ((Math.random() * 9 + 1) * 1000);
            System.out.println((int) ((Math.random() * 9 + 1) * 1000));
            StringBuffer newOrdNo = new StringBuffer();
            newOrdNo.append(orderNumber).append("_").append(String.valueOf(randNum));

            Payment payment = new Payment();

            /** 订单插入-默认状态待支付 */
            payment.setDate(new Date());
            payment.setTime(new Date());
//            payment.setAccountCode(this.account.getAccountCode());
            payment.setPackageID(electrical1.getServId());
            payment.setSubject(electrical1.getServPackageName());
            payment.setBody(electrical1.getServPackageName());
            payment.setPackageBeginDate(BeginDate);
            payment.setPackageEndDate(packageEndDate);
            payment.setOrderNumber(orderNumber);
            payment.setAlpayNumber("");
            payment.setPayamount(payamount);
            payment.setTranType(1);// tranType:交易类型 1、付款 2 退款
            payment.setPayType(Integer.parseInt(payType));// payType:支付宝 1、微信支付2
            payment.setStatus(1);// 支付状态：待支付
//            payment.setCreateUser(this.account.getUserName());
            payment.setCreateDate(new Date());

            payment.setIsNeedEquipment(isNeedEq);// 是否需要设备
            payment.setCashPledge(cash);// 设备押金
            payment.setIsNeedInvoice(isNeedInv);// 是否要发票
            payment.setInvoiceRemark(invoiceRemark);// 发票备注
            payment.setDistributionMode(distributionMode);// 运送方式
            payment.setShippingFee(fee);// 运费
            payment.setAddreeId(addId);// 地址id
            payment.setBuyAccounts(buyAcc); // 代买账户

            payment.setAmount(year);
            payment.setNewOrderNumber(newOrdNo.toString());
            payment.setDoctorId(Integer.parseInt(doctorId));
            payment.setEqAmount(Integer.parseInt(eqAmount));
            payment.setOrderStatus(orderStatus);
            log.info("*************************生成订单信息结束******************************************");
            /** 保存订单信息 */
            paymentService.savePayment(payment);
            log.info("*************************保存订单信息成功******************************************");
            log.info("*************************开始调用支付******************************************");
            Map<String, Object> orderMap = new HashMap<>();
            /** 支付宝支付 */
            if ("1".equals(payType.trim())) {
                log.info("*************************调用支付宝支付开始******************************************");
                String orderInfo = payService.toPay(payment);
                /* 订单信息 */
                orderMap.put("data", orderInfo);
                log.info("*************************调用支付宝支付结束******************************************");
            }
            /** 微信支付 */
            if ("2".equals(payType.trim())) {
                log.info("*************************调用微信支付开始******************************************");
                /** 统一下单 */
                Map<String, String> resultMap = payService.getWxOrder(payment);
                /* 订单信息 */
                orderMap.put("data", JSONObject.toJSONString(resultMap));
                log.info("*************************调用微信支付结束******************************************");
            }
            orderMap.put("orderNo", orderNumber);
            orderMap.put("success", true);

            log.info("预支付订单信息：" + JSONObject.toJSONString(orderMap));
            log.info("*************************调用支付结束******************************************");
            return RestResponseBo.ok(orderMap,1);
        } catch (Exception ex) {
            ex.printStackTrace();
            return RestResponseBo.fail(ex.getMessage());
        }
    }

    /** 重新 支付 */
    @RequestMapping(value = "/rePay", method = RequestMethod.POST)
    public RestResponseBo rePay(
            HttpServletRequest request,
            @RequestParam String pType, @RequestParam String userName, @RequestParam String orderNo
    ) {

        log.info("*************************重新支付方法调用开始******************************************");
        HashMap<String, Object> jsonMap = new HashMap<>();
        String message ;
        try {
            log.info("*************************重新生成支付信息开始******************************************");
            /** 判断账户是否存在 */
            if (StringUtils.isEmpty(userName)) {
                log.error("*************************请求参数有误！用户名不存在******************************************");
                message = "用户不存在！";
                return RestResponseBo.fail(message);
            }
//            this.account = accountService.getAccountByUserName(userName);
//            if (this.account == null) {
//                log.error("*************************用户不存在******************************************");
//                message = "用户不存在！";
//                return RestResponseBo.fail(message);
//            }
//            if (StringUtils.isEmpty(this.account.getAccountCode())) {
//                log.error("*************************请求参数有误！用户名不存在******************************************");
//                message = "用户不存在！";
//                return RestResponseBo.fail(message);
//            }

            /** 判断订单号是否合法 */
            if (StringUtils.isEmpty(orderNo)) {
                log.error("*************************请求参数有误！orderNo不存在******************************************");
                message = "订单号不存在！";
                return RestResponseBo.fail(message);
            }

            /** 判断支付类型 */
            if (StringUtils.isEmpty(pType)) {
                log.error("*************************请求参数有误！支付类型不能为空******************************************");
                message = "支付类型为空！";
                return RestResponseBo.fail(message);
            }

            if (!"1".equals(pType.trim()) && !"2".equals(pType.trim())) {
                log.error("*************************请求参数有误！支付类型不合法******************************************");
                message = "支付类型不合法！";
                return RestResponseBo.fail(message);
            }

            log.info("*************************查询订单信息开始******************************************");

            Payment rePayment = paymentService.getByOrderNumber(orderNo);
            if (rePayment == null) {
                message = "订单不存在！";
                return RestResponseBo.fail(message);
            }
            Integer num = rePayment.getAmount();
            if (num == null) {
                num = 1;
            }

            Date BeginDate = new Date();
            /** 套餐开始日期 */
            Calendar calendar = Calendar.getInstance();
            /** 套餐开始结束日期 */
            calendar.add(Calendar.YEAR, num);
            calendar.add(Calendar.HOUR_OF_DAY, 0);
            Date endDate = calendar.getTime();

            int randNum = (int) ((Math.random() * 9 + 1) * 1000);
            System.out.println((int) ((Math.random() * 9 + 1) * 1000));
            StringBuffer sb = new StringBuffer();
            sb.append(orderNo).append("_").append(String.valueOf(randNum));

            /** 订单插入-默认状态待支付 */
            rePayment.setDate(new Date());
            rePayment.setTime(new Date());
//            rePayment.setAccountCode(this.account.getAccountCode());

            rePayment.setPackageBeginDate(BeginDate);
            rePayment.setPackageEndDate(endDate);
            rePayment.setOrderNumber(orderNo);
            rePayment.setNewOrderNumber(sb.toString());
            rePayment.setPayType(Integer.parseInt(pType));

            /** 更新订单信息 */
            boolean b = paymentService.updatePayment(rePayment);
            log.info("*************************更新订单信息b:" + b);
            log.info("*************************更新订单信息成功******************************************");
            log.info("*************************开始调用支付******************************************");
            Map<String, Object> orderMap = new HashMap<>();
            /** 支付宝支付 */
            if ("1".equals(pType.trim())) {
                log.info("*************************调用支付宝支付开始******************************************");
                String orderInfo = payService.toPay(rePayment);
                /* 订单信息 */
                orderMap.put("data", orderInfo);
                log.info("*************************调用支付宝支付结束******************************************");
            }
            /** 微信支付 */
            if ("2".equals(pType.trim())) {
                log.info("*************************调用微信支付开始******************************************");
                /** 统一下单 */
                Map<String, String> resultMap = payService.getWxOrder(rePayment);
                /* 订单信息 */
                orderMap.put("data", JSONObject.toJSONString(resultMap));
                log.info("*************************调用微信支付结束******************************************");
            }
            orderMap.put("orderNo", orderNo);
            orderMap.put("success", true);
            log.info("预支付订单信息：" + JSONObject.toJSONString(orderMap));
            log.info("*************************调用支付结束******************************************");
            return RestResponseBo.ok(orderMap,1);
        } catch (Exception ex) {
            ex.printStackTrace();
            return RestResponseBo.fail(ex.getMessage());
        }
    }

    /** 支付宝支付异步回调通知 */
    @RequestMapping(value = "/alipayNotifyNotice", method = RequestMethod.POST)
    public String alipayNotifyNotice(HttpServletRequest request) {
        log.info("**************支付成功, 进入异步通知接口****************************************************");
        PrintWriter out = null;
        try {

            /** 获取支付宝POST过来反馈信息 **/
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"),
                // "utf-8");
                params.put(name, valueStr);
            }
            log.info("**************开始验证****************************************************");
            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.public_key, AlipayConfig.charset, "RSA2");
            /*
             * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
             * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
             * 3、校验通知中的seller_id（或者seller_email)
             * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
             * 4、验证app_id是否为该商户本身。
             */
            if (flag) {
                log.info("**************支付回调,验证成功****************************************************");
                // 商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                // 支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
                // 交易状态
                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                // 付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
                int status = getTradeStatus(trade_status);
                log.info("********************处理订单开始********************************");
                String newOrderNo = out_trade_no.substring(0, out_trade_no.length() - 5);
                 Payment payment = paymentService.getByOrderNumber(newOrderNo);
                if (payment.getStatus() == 2 || payment.getStatus() == 3) {
                    return "success";
                }
                log.info("********************更新订单表开始********************************");
                Integer num = payment.getAmount();
                if (num == null) {
                    num = 1;
                }
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.YEAR, num);
                calendar.add(Calendar.HOUR_OF_DAY, 0);
                /** 套餐开始结束日期 */
                Date endDate = calendar.getTime();
                /** 维护一下结束时间 */
                Payment p = new Payment();
                p.setOrderNumber(newOrderNo);
                p.setPackageEndDate(endDate);
                p.setOrderStatus("2");
                /** 更新订单信息 */
                paymentService.updatePayment(p);
                paymentService.updatePaymentStatus(newOrderNo, status, trade_no); // 更新表记录
                log.info("********************更新订单表结束********************************");

                // 支付成功
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                    log.info("********************开始插入服务包********************************");
                    // 处理支付成功逻辑
                    try {
                        /* 为多个账户插入购买逻辑 */
                        String buyAccounts = payment.getBuyAccounts();
                        /** 查出代买的账户 */
                        if (StringUtils.isEmpty(buyAccounts)) {
                            log.error("支付宝回调业务处理报错");
                            return "fail";
                        }
                        String[] accounts = buyAccounts.split(",");
//                        for (String x : accounts) {
//                            if (!StringUtils.isEmpty(payment) && !StringUtils.isEmpty(payment.getPackageID())) {
//                                SzPackageService packageService = szPackageServiceService
//                                        .getSzPackageServiceByServId(payment.getPackageID());
//                                if (!StringUtils.isEmpty(packageService)) {
//                                    // 插入服务包
//                                    SzAccountPackageLog szPackage = new SzAccountPackageLog();
//                                    szPackage.setAccountCode(Integer.parseInt(x));
//                                    szPackage.setIsFirstBuy(0);// 是否首次购买 1是 0否
//                                    szPackage.setPaymentId(payment.getPaymentID());
//                                    szPackage.setServId(payment.getPackageID());// 服务ID
//                                    szPackage.setServName(payment.getSubject());// 服务名称
//                                    szPackage.setServTime(packageService.getServTime());
//                                    szPackage.setDevsRentAmt(packageService.getFirstAmt());// 设备押金
//                                    szPackage.setServAmt(packageService.getAmt());// 服务包单价
//                                    szPackage.setServTalAmt(new BigDecimal(total_amount));// 用户最后支付的总金额
//                                    szPackage.setBuyServNum(payment.getAmount());// 购买的服务数量
////                                    szPackage.settEcgDevNo("");// 贴片心电设备号（首次购买有值）
////                                    szPackage.setkEcgDevNo("");// 卡片心电电设备号（首次购买有值）
//                                    szPackage.setDoctorId(payment.getDoctorId());// 默认医生ID:jingjueys02
//                                    szPackage.setPlatEmpId("");
//                                    szPackage.setPlatEmpName("");
//                                    szPackage.setType(packageService.getType());
//                                    szPackage.setCreateBy(payment.getUserName());
//                                    szPackage.setCreateTime(new Date());
//
////                                    if (szAccountPackageLogService.saveSzAccountPackageLog(szPackage)) {
////                                        log.info("********************插入服务包成功  账户为：" + x);
////                                    } else {
////                                        log.info("********************服务包保存失败账户为：" + x);
////                                    }
////
////                                    this.account = accountService.getAccountByAccountCode(Integer.parseInt(x));
////                                    if (this.account != null) {
////                                        boolean res = DysmsClient.sendMsg(SmsTemplate.BUY_SUCCESS,
////                                                this.account.getUserName(), payment.getSubject(),
////                                                payment.getOrderNumber());
////                                        log.info("********************支付成功向" + this.account.getUserName() + "发送短信:"
////                                                + res);
////                                    }
//
//                                } else {
//                                    log.info("********************服务包不存在********************************");
//                                   return "fail";
////                                }
//                            } else {
//                                log.info("********************订单不存在********************************");
//                                return "fail";
//                            }
//                        }
                        log.info("********************结束插入服务包********************************");
                        log.info("********************处理订单结束********************************");
                        return "success";


                    } catch (Exception e) {
                        log.error("支付宝回调业务处理报错,params:" + e);
                    }
                } else {
                    log.error("没有处理支付宝回调业务，支付宝交易状态：" + trade_status);
                }

            } else {
                log.error("******************************支付回调, 验签失败**********************");
                return "fail";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "fail";
    }

    /** 支付宝支付状态 */
    private int getTradeStatus(String tradeStatus) {
        // 判断交易结果
        switch (tradeStatus) {
            case "TRADE_FINISHED": // 交易结束并不可退款
                return 3;
            case "TRADE_SUCCESS": // 交易支付成功
                return 2;
            case "TRADE_CLOSED": // 未付款交易超时关闭或支付完成后全额退款
                return 1;
            case "WAIT_BUYER_PAY": // 交易创建并等待买家付款
                return 0;
            default:
                return -1;
        }
    }



    /** 微信支付异步回调通知 */
//    @RequestMapping(value = "/wxNotifyNotice", method = RequestMethod.POST)
//    public Object wxNotifyNotice(HttpServletRequest request) {
//
//        log.info("********************微信支付成功回调开始********************************");
//        PrintWriter out = ServletActionContext.getResponse().getWriter();
//        ServletActionContext.getResponse().setContentType(CONTENT_TYPE);
//
//        InputStream inStream = request.getInputStream();
//        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int len = 0;
//        while ((len = inStream.read(buffer)) != -1) {
//            outSteam.write(buffer, 0, len);
//        }
//        String resultxml = new String(outSteam.toByteArray(), "utf-8");
//        Map<String, String> params = WXPayUtil.xmlToMap(resultxml);
//        outSteam.close();
//        inStream.close();
//
//        // 测试数据：
//        log.info("********************返回数据：" + params);
//
//        Map<String, String> return_data = new HashMap<String, String>();
//        if (WXPayUtil.isSignatureValid(params, WeChatConfig.KEY)) {
//            log.info("********************微信支付延签成功********************************");
//            // 签名正确
//            // 进行处理。
//            // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
//            String total_fee = params.get("total_fee");
//            double v = Double.valueOf(total_fee) / 100;
//            String out_trade_no = params.get("out_trade_no");
//            String totalAmount = String.valueOf(v);
//            String tradeNo = params.get("transaction_id");
//            int status = params.get("result_code").equals("SUCCESS") ? 2 : 1;
//            log.info("********************处理订单开始********************************");
//            String newOrderNo = out_trade_no.substring(0, out_trade_no.length() - 5);
//           Payment payment = paymentService.getByOrderNumber(newOrderNo);
//            if (payment.getStatus() == 2 || payment.getStatus() == 3) {
//                return_data.put("return_code", "SUCCESS");
//                return_data.put("return_msg", "OK");
//                out.println(WXPayUtil.mapToXml(return_data));
//                return;
//            }
//            log.info("********************更新订单表开始********************************");
//            Integer num = payment.getAmount();
//            if (num == null) {
//                num = 1;
//            }
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.YEAR, num);
//            calendar.add(Calendar.HOUR_OF_DAY, 0);
//            /** 套餐开始结束日期 */
//            Date endDate = calendar.getTime();
//            /** 维护一下结束时间 */
//            Payment p = new Payment();
//            p.setOrderNumber(newOrderNo);
//            p.setPackageEndDate(endDate);
//            p.setOrderStatus("2");
//            /** 更新订单信息 */
//            paymentService.updatePayment(p);
//            paymentService.updatePaymentStatus(newOrderNo, status, tradeNo);
//            log.info("********************更新订单表结束********************************");
//
//            try {
//                /* 为多个账户插入购买逻辑 */
//                String buyAccounts = payment.getBuyAccounts();
//                /** 查出代买的账户 */
//                if (StringUtils.isEmpty(buyAccounts)) {
//                    log.error("微信回调业务处理报错，购买账户为空");
//                    return;
//                }
//                String[] accounts = buyAccounts.split(",");
//                for (String x : accounts) {
//                    if (!StringUtils.isEmpty(payment) && !StringUtils.isEmpty(payment.getPackageID())) {
//                        SzPackageService packageService = szPackageServiceService
//                                .getSzPackageServiceByServId(payment.getPackageID());
//                        if (!StringUtils.isEmpty(packageService)) {
//                            // 插入服务包
//                            SzAccountPackageLog szPackage = new SzAccountPackageLog();
//                            szPackage.setAccountCode(Integer.parseInt(x));
//                            szPackage.setIsFirstBuy(0);// 是否首次购买 1是
//                            // 0否
//                            szPackage.setPaymentId(payment.getPaymentID());
//                            szPackage.setServId(payment.getPackageID());// 服务ID
//                            szPackage.setServName(payment.getSubject());// 服务名称
//                            szPackage.setServTime(packageService.getServTime());
//                            szPackage.setDevsRentAmt(packageService.getFirstAmt());// 设备押金
//                            szPackage.setServAmt(packageService.getAmt());// 服务包单价
//                            szPackage.setServTalAmt(new BigDecimal(totalAmount));// 用户最后支付的总金额
//                            szPackage.setBuyServNum(payment.getAmount());// 购买的服务数量
//                            szPackage.settEcgDevNo("");// 贴片心电设备号（首次购买有值）
//                            szPackage.setkEcgDevNo("");// 卡片心电电设备号（首次购买有值）
//                            szPackage.setDoctorId(payment.getDoctorId());// 默认医生ID:jingjueys02
//                            szPackage.setPlatEmpId("");
//                            szPackage.setPlatEmpName("");
//                            szPackage.setType(packageService.getType());
//                            szPackage.setCreateBy(payment.getUserName());
//                            szPackage.setCreateTime(new Date());
//
//                            if (szAccountPackageLogService.saveSzAccountPackageLog(szPackage)) {
//                                log.info("********************插入服务包成功  账户为：" + x);
//                            } else {
//                                log.info("********************服务包保存失败账户为：" + x);
//                            }
//
//                            this.account = accountService.getAccountByAccountCode(Integer.parseInt(x));
//                            if (this.account != null) {
//                                boolean res = DysmsClient.sendMsg(SmsTemplate.BUY_SUCCESS, this.account.getUserName(),
//                                        payment.getSubject(), payment.getOrderNumber());
//                                log.info("********************支付成功向" + this.account.getUserName() + "发送短信:" + res);
//                            }
//                        } else {
//                            log.info("********************服务包不存在********************************");
//                        }
//                    } else {
//                        log.info("********************订单不存在********************************");
//
//                    }
//
//                }
//
//                return_data.put("return_code", "SUCCESS");
//                return_data.put("return_msg", "OK");
//                out.println(WXPayUtil.mapToXml(return_data));
//                log.info("********************结束插入服务包********************************");
//                log.info("********************处理订单结束********************************");
//
//            } catch (Exception e) {
//                log.error("微信回调业务处理报错,params:" + e);
//            }
//
//            // 通知微信已经收到消息，不要再给我发消息了，否则微信会8连击调用本接口
//
//        } else {
//            // 签名错误，如果数据里没有sign字段，也认为是签名错误
//            return_data.put("return_code", "FAIL");
//            return_data.put("return_msg", "验签失败");
//            out.println(WXPayUtil.mapToXml(return_data));
//        }
//
//        log.info("********************微信支付成功回调结束********************************");
//    }

    /** 获取支付结果的订单信息 */
//    @RequestMapping(value = "/getOrderDetail", method = RequestMethod.POST)
//    public RestResponseBo getOrderDetail(HttpServletRequest request, @RequestParam String orderNo) {
//
//        log.info("*************************获取订单完成之后的结果订单信息开始******************************************");
//        String message;
//        Payment payment = null;
//        if (!StringUtils.isEmpty(orderNo)) {
//            payment = paymentService.getByOrderNumber(orderNo);
//        }
//        if (payment == null) {
//            message = "未找到此订单信息";
//            return RestResponseBo.fail(message);
//        }
//        /** 取出account */
//        String buyAccounts = payment.getBuyAccounts();
//        if (StringUtils.isEmpty(buyAccounts)) {
//            Account acc = productService.getAccountByAccountCode(payment.getAccountCode());
//
//            payment.setBuyAccounts(acc.getUserName());
//        } else {
//
//            String[] accounts = buyAccounts.split(",");
//            StringBuffer sb = new StringBuffer();
//
//            for (String x : accounts) {
//                Account a = accountService.getAccountByAccountCode(Integer.parseInt(x));
//                if (a != null) {
//                    sb.append(a.getUserName().toString()).append(",");
//                }
//            }
//            log.info("*************************sb:" + sb + "************************");
//            if (sb.length() > 0) {
//                payment.setBuyAccounts(sb.substring(0, sb.length() - 1));
//            } else {
//                message = "当前的购买账户非法";
//                return RestResponseBo.fail(message);
//            }
//        }
//
//        Map<String, Object> orderMap = new HashMap<>();
//        orderMap.put("data", payment);
//        orderMap.put("success", true);
//        return RestResponseBo.ok(orderMap);
//    }

    /****** 获取用户购买记录 *****/
    @RequestMapping(value = "/getBuyecords", method = RequestMethod.POST)
    public RestResponseBo getBuyecords(HttpServletRequest request, @RequestParam String userName) {
        log.info("*************************获取用户购买记录信息开始******************************************");
        try {
            log.info("*************************生成支付信息开始******************************************");
             String message;
            /* 判断账户是否存在 */
            if (!StringUtils.isEmpty(userName)) {
//                this.account = accountService.getAccountByUserName(userName);

            } else {
                log.error("*************************请求参数有误！用户名不存在******************************************");
                message = "用户名不存在";
                return RestResponseBo.fail(message);
            }
//            if (this.account == null) {
//                log.error("*************************请求参数有误！用户名不存在******************************************");
//                message = "用户名不存在";
//                return RestResponseBo.fail(message);
//            }

            Map<String, Object> orderMap = new HashMap<>();
//            List<BuyRecord> records = productService.getBuyRecordList(this.account);
//            orderMap.put("data", records);
            orderMap.put("success", true);
           return RestResponseBo.ok(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("************************获取用户购买记录信息异常******************************************");
            return RestResponseBo.fail(e.getMessage());
        }

    }

    /** 获取套餐列表 */
//    @RequestMapping(value = "/getPackageList", method = RequestMethod.POST)
//    public RestResponseBo getPackageList(HttpServletRequest request, @RequestParam String userName) {
//        log.info("*************************获取用户购买记录信息开始******************************************");
//        try {
//            log.info("*************************生成支付信息开始******************************************");
//            String message;
//            /* 判断账户是否存在 */
//            if (!StringUtils.isEmpty(userName)) {
//                // this.account = accountService.getAccountByUserName(userName);
//
//            } else {
//                log.error("*************************请求参数有误！用户名不存在******************************************");
//                message = "用户名不存在";
//                return RestResponseBo.fail(message);
//            }
//            if (this.account == null) {
//                log.error("*************************请求参数有误！用户名不存在******************************************");
//                message = "用户名不存在";
//                return RestResponseBo.fail(message);
//            }
//
//            Map<String, Object> orderMap = new HashMap<>();
//            List<SzAccountPackageLog> packageLists = szAccountPackageLogService
//                    .getSzAccountPackageLogList(this.account.getAccountCode());
//            if (packageLists == null) {
//                packageLists = new ArrayList<>();
//            }
//            if (packageLists.size() > 0) {
//                packageLists.forEach(o -> {
//                    SzAccountService szAccountService = new SzAccountService();
//                    szAccountService.setPlId(o.getId());
//                    szAccountService.setAccountCode(o.getAccountCode());
//                    List<SzAccountService> accServices = productService.getServerList(szAccountService);
//                    o.setItemList(accServices);
//                });
//            }
//            orderMap.put("data", packageLists);
//            orderMap.put("success", true);
//            return RestResponseBo.ok(orderMap);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("************************获取用户购买记录信息异常******************************************");
//            return RestResponseBo.fail(e.getMessage());
//        }
//
//    }

    /****** 删除订单 *****/
    @RequestMapping(value = "/doDeleteOrder", method = RequestMethod.POST)
    public RestResponseBo doDeleteOrder(HttpServletRequest request, @RequestParam String userName, @RequestParam String orderNo) {
        log.info("*************************获取用户购买记录信息开始******************************************");

        try {
            log.info("*************************生成支付信息开始******************************************");
            String message;
            /* 判断账户是否存在 */
            if (!StringUtils.isEmpty(userName)) {
//                this.account = accountService.getAccountByUserName(userName);

            } else {
                log.error("*************************请求参数有误！用户名不存在******************************************");
                message = "用户名不存在";
                return RestResponseBo.fail(message);
            }
//            if (this.account == null) {
//                log.error("*************************请求参数有误！用户名不存在******************************************");
//                message = "用户名不存在";
//                return RestResponseBo.fail(message);
//            }
            /* 判断订单是否存在 */
            if (StringUtils.isEmpty(orderNo)) {
                log.error("*************************请求参数有误！用户名不存在******************************************");
                message = "订单号为空";
                return RestResponseBo.fail(message);
            }
            Payment payment = paymentService.getByOrderNumber(orderNo);
            if (payment == null) {
                message = "未找到此订单信息";
                return RestResponseBo.fail(message);
            }

            Map<String, Object> orderMap = new HashMap<>();
            boolean b = paymentService.deletePayment(payment);
            orderMap.put("data", b);
            orderMap.put("success", true);
            return RestResponseBo.ok(orderMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("************************获取用户购买记录信息异常******************************************");
            return RestResponseBo.fail(e.getMessage());
        }

    }




}
