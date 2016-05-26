package me.voler.jeveri.service.impl;

import me.voler.jeveri.service.Verification;
import me.voler.jeveri.service.VerificationService;

public abstract class VerificationServiceImpl implements VerificationService {

	@Override
	public Verification verify(String token) {
		return new Verification();
	}

}
