package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.Instance;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;

public class CommonUtils
{
	public static final String VERSION = "1.0";
	private static final byte[] IV = { 120, 7, 71, 53, 82, 112, 20, 8, 1, 19, 68, 26, 35, 56, 118, 23 };
	private static final byte[] CRYPT_KEY = { 55, 78, 54, 102, 106, 89, 70, 69, 112, 83, 71, 88, 80, 98, 55, 108 };

	private CommonUtils()
	{
	}

	/**
	 * Opens a new window and closes the current one.
	 * @param fxmlFileLocation the .fxml file location.
	 * @param title the title of the new window.
	 */
	public static void setNewWindow(String fxmlFileLocation, String title)
	{
		CommonUtils.setNewWindow(fxmlFileLocation, title, null, null);
	}

	/**
	 * Opens a new window and closes the current one.
	 * Executes an optional thread before showing the new window and another one after it has been shown.
	 * @param fxmlFileLocation the .fxml file location.
	 * @param title the title of the new window.
	 * @param firstThread the thread to execute before the window has been shown.
	 * @param secondThread the thread to execute after the window has been shown.
	 */
	public static void setNewWindow(String fxmlFileLocation, String title, Thread firstThread, Thread secondThread)
	{
		Platform.runLater(() -> {
			FXMLLoader fxmlLoader = new FXMLLoader(Instance.getInstance().getClass().getResource(fxmlFileLocation));
			try {
				Parent parent = fxmlLoader.load();
				Stage stage;
				if (Instance.getInstance().getWindowInformations() != null) {
					stage = Instance.getInstance().getWindowInformations().getStage();
				} else {
					stage = new Stage();
				}
				stage.setScene(new Scene(parent));
				stage.setTitle(title);
				stage.sizeToScene();
				stage.setResizable(false);
				if (firstThread != null) {
					firstThread.start();
				}
				if (Instance.getInstance().getWindowInformations() == null) {
					stage.show();
				}
				Instance.getInstance().setWindowInformations(new WindowInformations(fxmlLoader.getController(), stage));
				if (secondThread != null) {
					secondThread.start();
				}
			} catch (IOException exception) {
				Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		});
	}

	/**
	 * Closes the given window and all the parent ones.
	 * @param stage the first window to close.
	 */
	public static void closeAllWindows(Stage stage)
	{
		stage.close();
		if (stage.getOwner() != null) {
			CommonUtils.closeAllWindows((Stage) stage.getOwner());
		}
	}

	/**
	 * Checks whether a string can be parsed as an integer or not.
	 * @param string the string to be checked.
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
	 * Encrypt a string with a 128 bit key.
	 * @param text the string to be encrypted.
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
	 * Decrypt a string with a 128 bit key.
	 * @param text the string to be decrypted.
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
