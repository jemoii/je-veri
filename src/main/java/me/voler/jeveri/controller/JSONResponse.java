package me.voler.jeveri.controller;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class JSONResponse implements Serializable {

	private static final long serialVersionUID = -7467765639483707114L;

	private boolean status; // “业务”请求是否成功
	private Object obj;

	public JSONResponse() {
		this(StringUtils.EMPTY);
	}

	public JSONResponse(Object obj) {
		this(true, obj);
	}

	public JSONResponse(boolean status, Object obj) {
		this.status = status;
		this.obj = obj;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
