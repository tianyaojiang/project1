package com.langlang.health.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.github.wxpay.sdk.WXPayUtil;
import com.langlang.health.common.util.HttpUtil;
import com.langlang.health.config.AlipayConfig;
import com.langlang.health.config.WeChatConfig;
import com.langlang.health.pay.service.PayService;
import com.langlang.health.pay.entity.Payment;
import com.langlang.health.pay.service.PaymentService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tyj on 2019/01/11.
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private PaymentService paymentService;

    @Override
    public String toPay(Payment payment) {
        // 实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset,
                AlipayConfig.alipay_public_key, "RSA2");
        // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(payment.getBody());
        model.setSubject(payment.getSubject());
        model.setOutTradeNo(payment.getNewOrderNumber());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(payment.getPayamount().toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        // request.setReturnUrl(AlipayConfig.return_url);
        System.out.println("回调地址：" + AlipayConfig.notify_url);
        request.setNotifyUrl(AlipayConfig.notify_url);
        try {
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println("orderString:" + response.getBody());
            return response.getBody();
            // 就是orderString
            // 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> getWxOrder(Payment payment) throws Exception {

        BigDecimal a = payment.getPayamount();
        System.out.println("a:" + a);
        BigDecimal b = a.multiply(new BigDecimal(100));
        System.out.println("b:" + b);
        int total_fee = b.intValue();
        System.out.println("total_fee:" + total_fee);
        Long time = (System.currentTimeMillis() / 1000);

        Map<String, String> data = new HashMap<String, String>();
        data.put("appid", WeChatConfig.APPID);
        data.put("mch_id", WeChatConfig.MCH_ID);
        data.put("nonce_str", WXPayUtil.generateNonceStr());
        data.put("body", payment.getBody());
        data.put("out_trade_no", payment.getNewOrderNumber());
        data.put("total_fee", String.valueOf(total_fee));
        data.put("spbill_create_ip", WeChatConfig.SPBILL_CREATE_IP);
        data.put("notify_url", WeChatConfig.NOTIFY_URL);
        data.put("trade_type", "APP"); // 此处指定为app支付
        String sign = WXPayUtil.generateSignature(data, WeChatConfig.KEY);
        data.put("sign", sign);
        System.out.println("data:" + data);
        System.out.println("sign:" + sign);
        String newOrderNo = payment.getNewOrderNumber().substring(0, payment.getNewOrderNumber().length() - 5);
        System.out.println("新订单好:" + newOrderNo);

        Map<String, String> result = new HashMap<>();
        Map<String, String> parameterMap = new HashMap<>();
        try {
            String xml = WXPayUtil.mapToXml(data);
            String url = WeChatConfig.PLACEANORDER_URL;
            System.out.println("最终提交xml:" + xml);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(url);
            // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
            post.setEntity(new StringEntity(xml, "UTF-8"));
            post.addHeader("Content-Type", "text/xml");
            HttpResponse execute = httpClient.execute(post);
            HttpEntity entity = execute.getEntity();
            String responseContent = EntityUtils.toString(entity, "utf-8");
            System.out.println("获取数据:" + responseContent);

            String returnXml = HttpUtil.sendPost(url, xml);
            result = WXPayUtil.xmlToMap(returnXml);

            System.out.println("验证结果:" + WXPayUtil.isSignatureValid(result, WeChatConfig.KEY));

            result.put("timestamp", time.toString());
            System.out.println("returnXml:" + returnXml);
            System.out.println("result:" + result);
            /** 重新签名进行支付 */
            parameterMap.put("appid", result.get("appid"));
            parameterMap.put("partnerid", result.get("mch_id"));
            parameterMap.put("prepayid", result.get("prepay_id"));
            parameterMap.put("package", "Sign=WXPay");
            parameterMap.put("noncestr", result.get("nonce_str"));
            parameterMap.put("timestamp", result.get("timestamp"));
            String sign1 = WXPayUtil.generateSignature(parameterMap, WeChatConfig.KEY);
            parameterMap.put("sign", sign1);
            System.out.println("parameterMap结果:" + parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parameterMap;
    }


}
