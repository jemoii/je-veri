package me.voler.jeveri.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.api.internal.util.StringUtils;

import me.voler.jeveri.util.cache.BAECache;
import me.voler.jeveri.util.enumeration.SendError;
import me.voler.jeveri.util.enumeration.VerifyError;
import me.voler.jeveri.util.ticket.SignatureUtil;

@Controller
public class CacheController {

	@Resource
	private SignatureUtil SignatureUtil;

	@Resource
	private BAECache BAECache;

	private static final String BUSINESS_PREFIX = "qrlogin_";

	@ResponseBody
	@RequestMapping(value = "/cache/post", method = RequestMethod.POST)
	public JSONResponse putToCache(CacheInput input) {
		if (!SignatureUtil.checkSign(input)) {
			return new JSONResponse(false, SendError.SIGNATURE_ERROR);
		}
		if (!StringUtils.areNotEmpty(input.getKey(), input.getValue())) {
			return new JSONResponse(false, SendError.PARAM_ERROR);
		}
		BAECache.put(BUSINESS_PREFIX + input.getKey(), input.getValue(),
				input.getExpireTime() == null ? 5 * 60 : input.getExpireTime());
		return new JSONResponse(SendError.NONE_ERROR);
	}

	@ResponseBody
	@RequestMapping(value = "/cache/get", method = RequestMethod.GET)
	public JSONResponse getFromCache(@RequestParam String key) {
		String value = (String) BAECache.get(BUSINESS_PREFIX + key);
		if (value == null) {
			return new JSONResponse(false, VerifyError.TIMEOUT_ERROR);
		}
		return new JSONResponse(value);
	}

}
