package me.voler.jeveri.controller;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import me.voler.jeveri.service.Verification;
import me.voler.jeveri.service.VerificationService;
import me.voler.jeveri.util.enumeration.SendError;
import me.voler.jeveri.util.enumeration.VerifyError;
import me.voler.jeveri.util.ticket.SignatureUtil;

@Controller
public class IndexController {

	@Autowired
	@Qualifier("emailVerification")
	private VerificationService EmailVerification;
	@Autowired
	@Qualifier("messageVerification")
	private VerificationService MessageVerification;

	@Resource
	private SignatureUtil SignatureUtil;

	@ResponseBody
	@RequestMapping(value = "/delivery", method = RequestMethod.POST)
	public JSONResponse send(DeliveryInput input) {
		if (!SignatureUtil.checkSign(input)) {
			return new JSONResponse(false, SendError.SIGNATURE_ERROR);
		}
		SendError sendError = SendError.NONE_ERROR;
		Verification verification = new Verification();
		BeanUtils.copyProperties(input, verification);
		if (verification.getIdentity().indexOf('@') == -1) {
			sendError = MessageVerification.send(verification);
		} else {
			sendError = EmailVerification.send(verification);
		}
		if (sendError.getErrCode() < 0) {
			return new JSONResponse(false, sendError);
		} else {
			return new JSONResponse(sendError);
		}
	}

	/**
	 * 
	 * @param identity
	 *            手机号
	 * @param token
	 *            短信验证码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/matching", method = RequestMethod.GET)
	public JSONResponse verify(@RequestParam String identity, @RequestParam String token) {
		Verification verification = new Verification();
		verification.setIdentity(identity);
		verification.setToken(token);
		VerifyError verifyError = MessageVerification.verify(verification);
		if (verifyError.getErrCode() < 0) {
			return new JSONResponse(false, verifyError);
		} else {
			return new JSONResponse(verifyError);
		}
	}

	/**
	 * 邮箱验证成功且设置了应用回调链接，重定向至回调链接；否则返回空白页
	 * 
	 * @param token
	 *            邮箱验证token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ModelAndView verify(@RequestParam String token) {
		Verification verification = EmailVerification.verify(token);
		ModelAndView mv = new ModelAndView("check");
		if (verification == null || StringUtils.isEmpty(verification.getCallback())) {
			mv.addObject("verify", false);
			mv.addObject("callback", "http://duapp.voler.me/jeadmin/");
		} else {
			mv.addObject("verify", true);
			mv.addObject("callback",
					String.format("%s?id=%s&res=true", verification.getCallback(), verification.getIdentity()));
		}
		return mv;
	}

}
