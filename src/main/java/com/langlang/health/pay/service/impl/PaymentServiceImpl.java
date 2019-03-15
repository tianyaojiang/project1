package com.langlang.health.pay.service.impl;

import java.util.List;
import com.langlang.health.pay.entity.Payment;
import com.langlang.health.pay.service.PaymentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {


	@Override
	public boolean savePayment(Payment payment) {
		return false;
	}

	@Override
	public boolean updatePayment(Payment payment) {
		return false;
	}

	@Override
	public Payment getByOrderNumber(String orderNumber) {
		return null;
	}

	@Override
	public boolean updatePaymentStatus(String orderNumber, int status, String alpayNumber) {
		return false;
	}

	@Override
	public boolean deletePayment(Payment payment) {
		return false;
	}
}
