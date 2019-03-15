package com.langlang.health.pay.service;

import com.langlang.health.pay.entity.Payment;


public interface PaymentService {



	/**
	 * 订单插入
	 * 
	 * @return
	 */
	public boolean savePayment(Payment payment);

	public boolean updatePayment(Payment payment);

	/**
	 * 通过订单号获取订单信息
	 * 
	 * @param orderNumber
	 * @return
	 */
	public Payment getByOrderNumber(String orderNumber);

	/**
	 * 修改订单状态
	 * 
	 * @param orderNumber
	 *            订单号
	 * @param status
	 *            状态
	 * @param alpayNumber
	 *            支付宝流水号（非必填项）
	 * @return
	 */
	public boolean updatePaymentStatus(String orderNumber, int status, String alpayNumber);

	public boolean deletePayment(Payment payment);

}
