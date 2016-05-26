package me.voler.jeveri.util.message;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class MessageTemplate {

	private DefaultTaobaoClient taobaoClient;

	private static final Logger Log = LoggerFactory.getLogger(MessageTemplate.class);

	/**
	 * 
	 * @param req
	 * @return 发送验证短信是否成功
	 */
	public boolean send(AlibabaAliqinFcSmsNumSendRequest req) {

		try {
			AlibabaAliqinFcSmsNumSendResponse rsp = taobaoClient.execute(req);
			String responseBody = rsp.getBody();
			JsonNode okResponse = new ObjectMapper().readTree(responseBody)
					.get("alibaba_aliqin_fc_sms_num_send_response");
			if (okResponse == null) {
				JsonNode errorResponse = new ObjectMapper().readTree(responseBody).get("error_response");
				Log.error("message send error, sub_code: {}, https://api.alidayu.com/doc2/apiDetail.htm?apiId=25450",
						errorResponse.get("sub_code").asText());
				return false;
			}
			return true;
		} catch (IOException e) {
			Log.error("parse json error, error: {}", e.getMessage());
			return false;
		} catch (ApiException e) {
			Log.error("message send error, errMsg: {}", e.getErrMsg());
			return false;
		}

	}

	public DefaultTaobaoClient getTaobaoClient() {
		return taobaoClient;
	}

	public void setTaobaoClient(DefaultTaobaoClient taobaoClient) {
		this.taobaoClient = taobaoClient;
	}

}
