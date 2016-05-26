package me.voler.jeveri.util.ticket;

import java.security.SecureRandom;

/**
 * @see org.jasig.cas.util.DefaultUniqueTicketIdGenerator
 * @see org.jasig.cas.util.DefaultUniqueTicketIdGenerator
 * @see org.jasig.cas.util.DefaultRandomStringGenerator
 */
public class TicketGeneratorUtil {

	private int maxLength;
	private boolean existLetter;
	/** The array of printable characters to be used in our random string. */
	private static final char[] PRINTABLE_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ012345679"
			.toCharArray();
	private static final char[] FIGURE_CHARACTERS = "0123456789".toCharArray();

	public TicketGeneratorUtil(int maxLength, boolean existLetter) {
		this.maxLength = maxLength;
		this.existLetter = existLetter;
	}

	public String getNewTicket() {

		if (existLetter) {
			return this.getNewString(PRINTABLE_CHARACTERS);
		} else {
			return this.getNewString(FIGURE_CHARACTERS);
		}

	}

	private String getNewString(char[] chs) {
		byte[] random = new byte[this.maxLength];
		new SecureRandom().nextBytes(random);

		char[] output = new char[random.length];
		for (int i = 0; i < random.length; i++) {
			int index = Math.abs(random[i] % chs.length);
			output[i] = chs[index];
		}

		return new String(output);
	}

}
