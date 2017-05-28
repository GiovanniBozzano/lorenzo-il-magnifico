package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.Instance;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;

public class CommonUtils
{
	public static final String VERSION = "1.0";
	public static final String REGEX_REMOVE_TRAILING_SPACES = "^\\s+|\\s+$";
	public static final String REGEX_USERNAME = "^[\\w\\-]{4,16}$";
	private static final byte[] IV = { 120, 7, 71, 53, 82, 112, 20, 8, 1, 19, 68, 26, 35, 56, 118, 23 };
	private static final byte[] CRYPT_KEY = { 55, 78, 54, 102, 106, 89, 70, 69, 112, 83, 71, 88, 80, 98, 55, 108 };

	private CommonUtils()
	{
	}

	/**
	 * <p>Checks whether a string can be parsed as an integer or not.
	 *
	 * @param string the string to be checked.
	 *
	 * @return true if the string can be parsed, otherwise false.
	 */
	public static boolean isInteger(String string)
	{
		if (string.isEmpty()) {
			return false;
		}
		for (int index = 0; index < string.length(); index++) {
			if (index == 0 && string.charAt(index) == '-') {
				if (string.length() == 1) {
					return false;
				} else {
					continue;
				}
			}
			if (Character.digit(string.charAt(index), 10) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Encrypt a string with a 128 bit key.
	 *
	 * @param text the string to be encrypted.
	 *
	 * @return the encrypted string.
	 */
	public static String encrypt(String text)
	{
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(CommonUtils.CRYPT_KEY, "AES"), new IvParameterSpec(CommonUtils.IV));
			return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes()));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException exception) {
			Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		return null;
	}

	/**
	 * <p>Decrypt a string with a 128 bit key.
	 *
	 * @param text the string to be decrypted.
	 *
	 * @return the decrypted string.
	 */
	public static String decrypt(String text)
	{
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(CommonUtils.CRYPT_KEY, "AES"), new IvParameterSpec(CommonUtils.IV));
			return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException exception) {
			Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		return null;
	}
}
