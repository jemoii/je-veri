package me.voler.jeveri.service;

import java.io.Serializable;

public class CustomEmail implements Serializable {

	private static final long serialVersionUID = 3829463962238179688L;
	private static final String SUBJECT_TEMPLATE = "【舞乐】%s验证邮件";
	private static final String CONTENT_CONTENT = "<html>您正在进行%s操作，请在10分钟内访问如下链接验证邮箱：</br>"
			+ "<a href=\"http://duapp.voler.me/jeveri/check?token=%s\">http://duapp.voler.me/jeveri/check?token=%s</a></html>";

	private String type;
	private String token;
	private String to;
	private String remark;

	public String getSubject() {
		return String.format(SUBJECT_TEMPLATE, type);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return String.format(CONTENT_CONTENT, type, token, token);
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
