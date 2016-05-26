package me.voler.jeveri.controller;

public class CacheInput extends RESTRequest {

	private static final long serialVersionUID = -6780280702899668438L;
	
	private String key;
	private String value;
	private Integer expireTime;

	public final String getKey() {
		return key;
	}

	public final void setKey(String key) {
		this.key = key;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

	public final Integer getExpireTime() {
		return expireTime;
	}

	public final void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}

}
