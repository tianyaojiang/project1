package com.langlang.health;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.langlang.health.common.util.sms.DysmsClient;
import com.langlang.health.common.util.sms.SmsConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {

	@Test
	public void test() {
		System.out.println(SmsConfig.SmsTemplate.REGISTER.getCode());
    	DysmsClient.sendCode(SmsConfig.SmsTemplate.REGISTER, "18817343060");
	}

}
