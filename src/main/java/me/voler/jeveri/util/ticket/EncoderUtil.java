package me.voler.jeveri.util.ticket;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @see org.jasig.cas.authentication.handler.DefaultPasswordEncoder
 *
 */
public class EncoderUtil {
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	private static String encodingAlgorithm = "MD5";

	public static String encode(final String text, String encodingAlgorithm) {
		EncoderUtil.encodingAlgorithm = encodingAlgorithm;
		return encode(text);
	}

	public static String encode(final String text) {
		if (text == null) {
			return null;
		}

		try {
			MessageDigest messageDigest = MessageDigest.getInstance(EncoderUtil.encodingAlgorithm);

			messageDigest.update(text.getBytes());
			final byte[] digest = messageDigest.digest();

			return getFormattedText(digest);
		} catch (final NoSuchAlgorithmException e) {
			throw new SecurityException(e);
		}
	}

	private static String getFormattedText(final byte[] bytes) {
		final StringBuilder buf = new StringBuilder(bytes.length * 2);

		for (int j = 0; j < bytes.length; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

}
