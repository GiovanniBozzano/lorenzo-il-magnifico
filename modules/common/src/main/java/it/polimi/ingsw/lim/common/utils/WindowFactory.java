package it.polimi.ingsw.lim.common.utils;

import com.jfoenix.controls.JFXNodesList;
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
	 * <p>Opens a new window
	 *
	 * @param fxmlFileLocation the .fxml file location.
	 */
	public void setNewWindow(String fxmlFileLocation)
	{
		this.setNewWindow(fxmlFileLocation, null, null);
	}

	public void setNewWindow(String fxmlFileLocation, Runnable postShowing)
	{
		this.setNewWindow(fxmlFileLocation, postShowing, null);
	}

	/**
	 * <p>Opens a new window. Executes an optional thread before showing the new
	 * window and another one after it has been shown.
	 *
	 * @param fxmlFileLocation the .fxml file location.
	 * @param postShowing the thread to execute after the window has been
	 * shown.
	 */
	public void setNewWindow(String fxmlFileLocation, Runnable postShowing, Stage stage)
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
			Stage newStage;
			if (this.currentWindow.get() == null && stage == null) {
				newStage = new Stage();
				newStage.initStyle(StageStyle.UNDECORATED);
				newStage.setResizable(false);
				newStage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
					if (!newValue) {
						newStage.getScene().setCursor(Cursor.HAND);
						newStage.getScene().setCursor(Cursor.DEFAULT);
					}
				});
			} else if (this.currentWindow.get() != null) {
				newStage = this.currentWindow.get().getStage();
			} else {
				newStage = stage;
				newStage.initStyle(StageStyle.UNDECORATED);
				newStage.setResizable(false);
				newStage.iconifiedProperty().addListener((observable, oldValue, newValue) -> {
					if (!newValue) {
						newStage.getScene().setCursor(Cursor.HAND);
						newStage.getScene().setCursor(Cursor.DEFAULT);
					}
				});
			}
			this.currentWindow.set(fxmlLoader.getController());
			((CustomController) fxmlLoader.getController()).setStage(newStage);
			newStage.setScene(new Scene(parent));
			newStage.centerOnScreen();
			Platform.runLater(() -> {
				newStage.show();
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

	public static boolean isNodesListExpanded(JFXNodesList nodesList)
	{
		try {
			Field fieldExpanded = nodesList.getClass().getDeclaredField("expanded");
			fieldExpanded.setAccessible(true);
			return fieldExpanded.getBoolean(nodesList);
		} catch (NoSuchFieldException | IllegalAccessException exception) {
			Instance.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
		return false;
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
