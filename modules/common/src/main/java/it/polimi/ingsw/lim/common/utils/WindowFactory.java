package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.gui.CustomController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class WindowFactory
{
	private static final WindowFactory INSTANCE = new WindowFactory();
	private final ObservableMap<Stage, CustomController> openWindows = FXCollections.observableHashMap();
	private final ObjectProperty<WindowInformations> currentWindow = new SimpleObjectProperty<>(null);

	private WindowFactory()
	{
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

	public static WindowFactory getInstance()
	{
		return WindowFactory.INSTANCE;
	}

	/**
	 * <p>Opens a new window and closes the current one.
	 *
	 * @param fxmlFileLocation the .fxml file location.
	 * @param closeOthers whether to close the other windows or not.
	 */
	public void setNewWindow(String fxmlFileLocation, boolean closeOthers)
	{
		this.setNewWindow(fxmlFileLocation, closeOthers, null);
	}

	/**
	 * <p>Opens a new window and closes the current one. Executes an optional
	 * thread before showing the new window and another one after it has been
	 * shown.
	 *
	 * @param fxmlFileLocation the .fxml file location.
	 * @param closeOthers whether to close the other windows or not.
	 * @param postShowing the thread to execute after the window has been
	 * shown.
	 */
	public void setNewWindow(String fxmlFileLocation, boolean closeOthers, Runnable postShowing)
	{
		Platform.runLater(() -> {
			FXMLLoader fxmlLoader = new FXMLLoader(Instance.getInstance().getClass().getResource(fxmlFileLocation));
			Parent parent;
			try {
				parent = fxmlLoader.load();
			} catch (IOException exception) {
				Instance.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				return;
			}
			Stage stage = new Stage();
			stage.addEventHandler(WindowEvent.WINDOW_SHOWN, event -> this.openWindows.put(stage, fxmlLoader.getController()));
			stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, event -> this.openWindows.remove(stage));
			stage.focusedProperty().addListener((observable, wasFocused, isNowFocused) -> {
				if (isNowFocused) {
					this.currentWindow.set(new WindowInformations(fxmlLoader.getController(), stage));
				}
			});
			stage.setScene(new Scene(parent));
			stage.sizeToScene();
			stage.initStyle(StageStyle.UNDECORATED);
			//stage.setResizable(false);
			if (closeOthers) {
				this.closeAllWindows();
			}
			stage.show();
			((CustomController) fxmlLoader.getController()).setupGui();
			if (postShowing != null) {
				ExecutorService executorService = Executors.newSingleThreadExecutor();
				executorService.execute(postShowing);
				executorService.shutdown();
			}
		});
	}

	/**
	 * <p>Closes the given window and all the parent ones.
	 */
	public void closeAllWindows()
	{
		for (Stage closingStage : this.openWindows.keySet()) {
			this.closeWindow(closingStage);
		}
	}

	private void closeWindow(Stage stage)
	{
		this.openWindows.remove(stage);
		stage.close();
	}

	public WindowInformations getCurrentWindow()
	{
		return this.currentWindow.get();
	}

	public boolean isWindowOpen(Class<? extends CustomController> clazz)
	{
		for (CustomController controller : this.openWindows.values()) {
			if (controller.getClass() == clazz) {
				return true;
			}
		}
		return false;
	}
}
