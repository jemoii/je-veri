package me.voler.jeveri.util.ticket;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import me.voler.jeveri.controller.RESTRequest;

@Service
public class SignatureUtil {

	private static final String SECRET_KEY = "jemoii.duapp.com";

	public HashMap<String, String> getter(Object obj) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		if (!(obj instanceof RESTRequest)) {
			return paramMap;
		}
		RESTRequest request = (RESTRequest) obj;

		try {
			paramMap.put("timestamp", DateFormatUtils.format(request.getTimestamp(), "yyyy-MM-dd HH:mm:ss"));
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("request param can not be null");
		}
		Method[] methods = request.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			if (methodName.startsWith("get") && methods[i].getParameterTypes().length == 0) {
				String fieldName = methodName.substring("get".length());
				fieldName = Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
				//
				if (fieldName.equals("class")) {
					continue;
				}
				try {
					Object result = methods[i].invoke(request);
					//
					if (result == null) {
						paramMap.put(fieldName, null);
					} else {
						paramMap.put(fieldName, String.valueOf(result));
					}

				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

			}
		}
		return paramMap;

	}

	public boolean checkSign(RESTRequest request) {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		try {
			paramMap = getter(request);
			// System.err.println(JSON.toJSONString(paramMap, true));
		} catch (IllegalArgumentException e) {
			return false;
		}

		String[] keyArray = paramMap.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < keyArray.length; i++) {
			String value = paramMap.get(keyArray[i]);
			if (StringUtils.isNotEmpty(value)) {
				builder.append(keyArray[i]).append("=").append(value);
				if (i != keyArray.length - 1) {
					builder.append("&");
				}
			}
		}
		builder.append(SECRET_KEY);
		// System.err.println(builder.toString());
		return EncoderUtil.encode(builder.toString()).equals(request.getSign());
	}

}
