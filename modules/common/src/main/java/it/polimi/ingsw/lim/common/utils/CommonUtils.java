package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.Instance;
import javafx.scene.text.Font;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
	public static final Font ROBOTO_BLACK = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-Black.ttf"), 12);
	public static final Font ROBOTO_BOLD = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-Bold.ttf"), 12);
	public static final Font ROBOTO_BOLD_ITALIC = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-BoldItalic.ttf"), 12);
	public static final Font ROBOTO_ITALIC = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-Italic.ttf"), 12);
	public static final Font ROBOTO_LIGHT = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-Light.ttf"), 12);
	public static final Font ROBOTO_LIGHT_ITALIC = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-LightItalic.ttf"), 12);
	public static final Font ROBOTO_MEDIUM = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-Medium.ttf"), 12);
	public static final Font ROBOTO_MEDIUM_ITALIC = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-MediumItalic.ttf"), 12);
	public static final Font ROBOTO_REGULAR = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-Regular.ttf"), 12);
	public static final Font ROBOTO_THIN = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-Thin.ttf"), 12);
	public static final Font ROBOTO_THIN_ITALIC = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/Roboto-ThinItalic.ttf"), 12);
	public static final Font ROBOTO_CONDENSED_BOLD = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/RobotoCondensed-Bold.ttf"), 12);
	public static final Font ROBOTO_CONDENSED_BOLD_ITALIC = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/RobotoCondensed-BoldItalic.ttf"), 12);
	public static final Font ROBOTO_CONDENSED_ITALIC = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/RobotoCondensed-Italic.ttf"), 12);
	public static final Font ROBOTO_CONDENSED_LIGHT = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/RobotoCondensed-Light.ttf"), 12);
	public static final Font ROBOTO_CONDENSED_LIGHT_ITALIC = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/RobotoCondensed-LightItalic.ttf"), 12);
	public static final Font ROBOTO_CONDENSED_REGULAR = Font.loadFont(CommonUtils.class.getResourceAsStream("/font/roboto/RobotoCondensed-Regular.ttf"), 12);

	private CommonUtils()
	{
	}

	static public String exportResource(String resourceName, String fileName) throws Exception
	{
		String jarFolder = new File(CommonUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');
		InputStream inputStream = CommonUtils.class.getResourceAsStream(resourceName);
		OutputStream outputStream = new FileOutputStream(jarFolder + fileName);
		if (inputStream == null) {
			throw new Exception("Cannot get resource \"" + resourceName + "\" from jar file.");
		}
		int readBytes;
		byte[] buffer = new byte[4096];
		while ((readBytes = inputStream.read(buffer)) > 0) {
			outputStream.write(buffer, 0, readBytes);
		}
		inputStream.close();
		outputStream.close();
		return jarFolder + fileName;
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
			Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
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
			Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
		return null;
	}
}
