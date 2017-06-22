package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.gui.CustomController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;

public class WindowFactory
{
	private static final WindowFactory INSTANCE = new WindowFactory();
	public static final Semaphore WINDOW_OPENING_SEMAPHORE = new Semaphore(1);
	private final ObjectProperty<CustomController> currentWindow = new SimpleObjectProperty<>(null);

	private WindowFactory()
	{
	}

	/**
	 * <p>Opens a new window and closes the current one.
	 *
	 * @param fxmlFileLocation the .fxml file location.
	 */
	public void setNewWindow(String fxmlFileLocation)
	{
		this.setNewWindow(fxmlFileLocation, null);
	}

	/**
	 * <p>Opens a new window and closes the current one. Executes an optional
	 * thread before showing the new window and another one after it has been
	 * shown.
	 *
	 * @param fxmlFileLocation the .fxml file location.
	 * @param postShowing the thread to execute after the window has been
	 * shown.
	 */
	public void setNewWindow(String fxmlFileLocation, Runnable postShowing)
	{
		try {
			WindowFactory.WINDOW_OPENING_SEMAPHORE.acquire();
		} catch (InterruptedException exception) {
			Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			Thread.currentThread().interrupt();
		}
		Platform.runLater(() -> {
			FXMLLoader fxmlLoader = new FXMLLoader(Instance.getInstance().getClass().getResource(fxmlFileLocation));
			Parent parent;
			try {
				parent = fxmlLoader.load();
			} catch (IOException exception) {
				Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				return;
			}
			Stage stage;
			if (this.currentWindow.get() == null) {
				stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.setResizable(false);
				stage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
					if (!newValue) {
						stage.getScene().setCursor(Cursor.HAND);
						stage.getScene().setCursor(Cursor.DEFAULT);
					}
				});
			} else {
				stage = this.currentWindow.get().getStage();
			}
			this.currentWindow.set(fxmlLoader.getController());
			((CustomController) fxmlLoader.getController()).setStage(stage);
			stage.setScene(new Scene(parent));
			stage.centerOnScreen();
			Platform.runLater(() -> {
				stage.show();
				((CustomController) fxmlLoader.getController()).setupGui();
				if (postShowing != null) {
					ExecutorService executorService = Executors.newSingleThreadExecutor();
					executorService.execute(postShowing);
					executorService.shutdown();
				}
			});
			WindowFactory.WINDOW_OPENING_SEMAPHORE.release();
		});
	}

	/**
	 * <p>Closes the current window.
	 */
	public void closeWindow()
	{
		if (this.currentWindow.get() != null) {
			this.currentWindow.get().getStage().close();
		}
	}

	public static void setTooltipOpenDelay(Tooltip tooltip, double milliseconds)
	{
		try {
			Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
			fieldBehavior.setAccessible(true);
			Object objectBehavior = fieldBehavior.get(tooltip);
			Field fieldTimer = objectBehavior.getClass().getDeclaredField("activationTimer");
			fieldTimer.setAccessible(true);
			Timeline objectTimer = (Timeline) fieldTimer.get(objectBehavior);
			objectTimer.getKeyFrames().clear();
			objectTimer.getKeyFrames().add(new KeyFrame(new Duration(milliseconds)));
		} catch (NoSuchFieldException | IllegalAccessException exception) {
			Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public static void setTooltipVisibleDuration(Tooltip tooltip, double milliseconds)
	{
		try {
			Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
			fieldBehavior.setAccessible(true);
			Object objectBehavior = fieldBehavior.get(tooltip);
			Field fieldTimer = objectBehavior.getClass().getDeclaredField("hideTimer");
			fieldTimer.setAccessible(true);
			Timeline objectTimer = (Timeline) fieldTimer.get(objectBehavior);
			objectTimer.getKeyFrames().clear();
			if (milliseconds >= 0) {
				objectTimer.getKeyFrames().add(new KeyFrame(new Duration(milliseconds)));
			} else {
				objectTimer.getKeyFrames().add(new KeyFrame(Duration.INDEFINITE));
			}
		} catch (NoSuchFieldException | IllegalAccessException exception) {
			Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public boolean isWindowOpen(Class<? extends CustomController> clazz)
	{
		return this.currentWindow.get().getClass() == clazz;
	}

	public void disableWindow()
	{
		if (this.currentWindow.get() != null) {
			this.currentWindow.get().getStage().getScene().getRoot().setDisable(true);
		}
	}

	public void enableWindow()
	{
		if (this.currentWindow.get() != null) {
			this.currentWindow.get().getStage().getScene().getRoot().setDisable(false);
		}
	}

	public static WindowFactory getInstance()
	{
		return WindowFactory.INSTANCE;
	}

	public CustomController getCurrentWindow()
	{
		return this.currentWindow.get();
	}
}
