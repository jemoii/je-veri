package me.voler.jeveri.controller;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class RESTRequest implements Serializable {

	private static final long serialVersionUID = 125196094258931071L;

	private String sign;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date timestamp;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
