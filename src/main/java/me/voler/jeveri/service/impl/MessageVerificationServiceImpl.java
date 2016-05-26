package me.voler.jeveri.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;

import me.voler.jeveri.service.Verification;
import me.voler.jeveri.util.cache.BAECache;
import me.voler.jeveri.util.enumeration.SendError;
import me.voler.jeveri.util.enumeration.VerifyError;
import me.voler.jeveri.util.message.MessageTemplate;
import me.voler.jeveri.util.ticket.TicketGeneratorUtil;

@Service("messageVerification")
public class MessageVerificationServiceImpl extends VerificationServiceImpl {

	@Autowired
	@Qualifier("messageTemplate")
	private MessageTemplate MessageTemplate;

	@Resource
	private BAECache BAECache;

	private static final String SMS_PARAM_STRING = "{\"code\":\"%s\"}";

	@Override
	public SendError send(Verification original) {
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("extend");
		req.setSmsType("normal");
		req.setSmsFreeSignName("舞乐");
		TicketGeneratorUtil ticketGenerator = new TicketGeneratorUtil(4, false);
		String token = ticketGenerator.getNewTicket();
		req.setSmsParamString(String.format(SMS_PARAM_STRING, token));
		if (StringUtils.isEmpty(original.getIdentity())) {
			return SendError.PARAM_ERROR;
		}
		req.setRecNum(original.getIdentity());
		req.setSmsTemplateCode("SMS_9655861");
		// appSecret error
		// System.err.println(JSONObject.toJSONString(req));

		if (MessageTemplate.send(req)) {
			BAECache.put(original.getIdentity(), token, 10 * 60);
			return SendError.NONE_ERROR;
		} else {
			return SendError.TEMPLATE_ERROR;
		}
	}

	@Override
	public VerifyError verify(Verification custom) {
		String token = (String) BAECache.get(custom.getIdentity());
		if (token == null) {
			return VerifyError.TIMEOUT_ERROR;
		} else if (!token.equals(custom.getToken())) {
			return VerifyError.NOT_EQUAL_ERROR;
		} else {
			return VerifyError.NONE_ERROR;
		}
	}

}
