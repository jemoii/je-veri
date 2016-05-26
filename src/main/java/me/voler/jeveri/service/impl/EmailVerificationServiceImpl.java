package me.voler.jeveri.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.taobao.api.internal.util.StringUtils;

import me.voler.jeveri.service.CustomEmail;
import me.voler.jeveri.service.Verification;
import me.voler.jeveri.util.cache.BAECache;
import me.voler.jeveri.util.email.EmailTemplate;
import me.voler.jeveri.util.enumeration.BusinessType;
import me.voler.jeveri.util.enumeration.SendError;
import me.voler.jeveri.util.enumeration.VerifyError;
import me.voler.jeveri.util.ticket.HashUtil;

@Service("emailVerification")
public class EmailVerificationServiceImpl extends VerificationServiceImpl {

	@Autowired
	@Qualifier("emailTemplate")
	private EmailTemplate EmailTemplate;

	@Resource
	private BAECache BAECache;

	@Override
	public SendError send(Verification original) {
		CustomEmail email = new CustomEmail();
		if (original.getBusinessType() == null || !BusinessType.validateCode(original.getBusinessType())) {
			return SendError.PARAM_ERROR;
		}
		email.setType(BusinessType.getTypeMsg(original.getBusinessType()));
		String token = HashUtil.randomKey();
		email.setToken(token);
		if (!StringUtils.areNotEmpty(original.getIdentity(), original.getCallback())) {
			return SendError.PARAM_ERROR;
		}
		email.setTo(original.getIdentity());
		if (EmailTemplate.send(email)) {
			BAECache.put(token, original, 10 * 60);
			return SendError.NONE_ERROR;
		} else {
			return SendError.TEMPLATE_ERROR;
		}
	}

	@Override
	public VerifyError verify(Verification custom) {
		Verification original = (Verification) BAECache.get(custom.getToken());
		if (original == null) {
			return VerifyError.TIMEOUT_ERROR;
		} else {
			// callback
			return VerifyError.NONE_ERROR;
		}
	}

	@Override
	public Verification verify(String token) {
		return (Verification) BAECache.get(token);
	}

}
