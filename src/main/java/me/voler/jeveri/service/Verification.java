package me.voler.jeveri.service;

import java.io.Serializable;

public class Verification implements Serializable {

	private static final long serialVersionUID = -1046415258253984783L;

	private Integer businessType; // 需要验证的业务类型，如“注册”、“找回密码”
	private String identity;
	private String token;
	private String callback; // 用于邮件验证时回调业务应用，短信验证时缺省

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

}
