package me.voler.jeveri.test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import me.voler.jeveri.controller.DeliveryInput;
import me.voler.jeveri.util.ticket.SignatureUtil;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "file:src/main/webapp/WEB-INF/jeveri-servlet.xml" })
public class SignTest {

	@Resource
	private SignatureUtil SignatureUtil;

	@Test
	public void testSign() {
		DeliveryInput input = new DeliveryInput();
		input.setBusinessType(1);
		input.setIdentity("jemoii@163.com");
		input.setCallback("http://duapp.voler.me/jeadmin");
		input.setTimestamp(new Date());
		System.err.println(SignatureUtil.checkSign(input));
	}

}
