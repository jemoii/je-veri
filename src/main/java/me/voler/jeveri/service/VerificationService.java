package me.voler.jeveri.service;

import me.voler.jeveri.util.enumeration.SendError;
import me.voler.jeveri.util.enumeration.VerifyError;

public interface VerificationService {

	/**
	 * 
	 * @param original
	 * @return 发送结果
	 * 
	 * @see me.voler.jeveri.util.enumeration.SendError
	 */
	SendError send(Verification original);

	/**
	 * 
	 * @param custom
	 * @return 验证结果
	 * 
	 * @see me.voler.jeveri.util.enumeration.VerifyError
	 */
	VerifyError verify(Verification custom);

	/**
	 * 
	 * @param token
	 * @return 缓存中与token对应的Verification实例
	 */
	Verification verify(String token);

}
