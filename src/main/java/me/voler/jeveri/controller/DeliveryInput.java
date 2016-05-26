package me.voler.jeveri.controller;

public class DeliveryInput extends RESTRequest {

	private static final long serialVersionUID = 4671802182311088776L;

	private Integer businessType;
	private String identity;
	private String callback;

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

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

}
