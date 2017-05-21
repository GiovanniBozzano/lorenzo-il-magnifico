package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.gui.IController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.lang.reflect.Field;
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
	 * Opens a new window and closes the current one.
	 *
	 * @param fxmlFileLocation the .fxml file location.
	 * @param title the title of the new window.
	 */
	public static void setNewWindow(String fxmlFileLocation, String title)
	{
		CommonUtils.setNewWindow(fxmlFileLocation, title, null);
	}

	/**
	 * Opens a new window and closes the current one. Executes an optional
	 * thread before showing the new window and another one after it has been
	 * shown.
	 *
	 * @param fxmlFileLocation the .fxml file location.
	 * @param title the title of the new window.
	 * @param postShowingThread the thread to execute after the window has been
	 * shown.
	 */
	public static void setNewWindow(String fxmlFileLocation, String title, Thread postShowingThread)
	{
		Platform.runLater(() -> {
			FXMLLoader fxmlLoader = new FXMLLoader(Instance.getInstance().getClass().getResource(fxmlFileLocation));
			try {
				Parent parent = fxmlLoader.load();
				Stage stage;
				if (Instance.getInstance().getWindowInformations() == null) {
					stage = new Stage();
				} else {
					stage = Instance.getInstance().getWindowInformations().getStage();
				}
				stage.setScene(new Scene(parent));
				stage.setTitle(title);
				stage.sizeToScene();
				stage.setResizable(false);
				if (Instance.getInstance().getWindowInformations() == null) {
					stage.show();
				}
				((IController) fxmlLoader.getController()).setupGui();
				Instance.getInstance().setWindowInformations(new WindowInformations(fxmlLoader.getController(), stage));
				if (postShowingThread != null) {
					postShowingThread.start();
				}
			} catch (IOException exception) {
				Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			}
		});
	}

	/**
	 * Closes the given window and all the parent ones.
	 *
	 * @param stage the first window to close.
	 */
	public static void closeAllWindows(Stage stage)
	{
		stage.close();
		if (stage.getOwner() != null) {
			CommonUtils.closeAllWindows((Stage) stage.getOwner());
		}
	}

	public static void setTooltipDelay(Tooltip tooltip, double milliseconds)
	{
		try {
			Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
			fieldBehavior.setAccessible(true);
			Object objBehavior = fieldBehavior.get(tooltip);
			Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
			fieldTimer.setAccessible(true);
			Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);
			objTimer.getKeyFrames().clear();
			objTimer.getKeyFrames().add(new KeyFrame(new Duration(milliseconds)));
		} catch (NoSuchFieldException | IllegalAccessException exception) {
			Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	/**
	 * Checks whether a string can be parsed as an integer or not.
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
	 * Encrypt a string with a 128 bit key.
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
	 * Decrypt a string with a 128 bit key.
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
