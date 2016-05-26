package me.voler.jeveri.util.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.voler.jeveri.service.CustomEmail;

public class EmailTemplate {

	private HtmlEmail email;

	private static final Logger Log = LoggerFactory.getLogger(EmailTemplate.class);

	/**
	 * 
	 * @param customEmail
	 * @return 发送验证邮件是否成功
	 */
	public boolean send(CustomEmail customEmail) {

		try {
			email.setSSLOnConnect(true);
			email.setSubject(customEmail.getSubject());
			email.setHtmlMsg(customEmail.getContent());
			email.addTo(customEmail.getTo());

			email.send();
			return true;
		} catch (IllegalStateException e) {
			// TODO connect to the smtp server failed
			// java.lang.IllegalStateException:
			// The mail session is already initialized
			Log.error("email send error, error:{}", e.getMessage());
			return false;
		} catch (EmailException e) {
			Log.error("email send error, error:{}", e.getMessage());
			return false;
		}

	}

	public HtmlEmail getEmail() {
		return email;
	}

	public void setEmail(HtmlEmail email) {
		this.email = email;
	}
}
