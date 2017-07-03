package it.polimi.ingsw.lim.common.view.gui;

import it.polimi.ingsw.lim.common.Instance;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class CustomController implements Initializable
{
	@FXML private StackPane stackPane;
	private Stage stage;
	private boolean initialized = false;
	private double xOffset;
	private double yOffset;

	@FXML
	private void handleStackPaneMousePressed(MouseEvent event)
	{
		this.stackPane.getScene().getRoot().requestFocus();
		this.xOffset = this.stackPane.getScene().getWindow().getX() - event.getScreenX();
		this.yOffset = this.stackPane.getScene().getWindow().getY() - event.getScreenY();
	}

	@FXML
	private void handleStackPaneMouseDragged(MouseEvent event)
	{
		this.stackPane.getScene().getWindow().setX(event.getScreenX() + this.xOffset);
		this.stackPane.getScene().getWindow().setY(event.getScreenY() + this.yOffset);
	}

	@FXML
	private void handleQuitImageViewMouseClicked()
	{
		this.getStage().getScene().getRoot().setDisable(true);
		Instance.getInstance().stop();
	}

	@FXML
	private void handleQuitImageViewMouseEntered()
	{
		this.stackPane.getScene().setCursor(Cursor.HAND);
	}

	@FXML
	private void handleQuitImageViewMouseExited()
	{
		this.stackPane.getScene().setCursor(Cursor.DEFAULT);
	}

	@FXML
	private void handleMinimizeImageViewMouseClicked()
	{
		((Stage) this.stackPane.getScene().getWindow()).setIconified(true);
	}

	@FXML
	private void handleMinimizeImageViewMouseEntered()
	{
		this.stackPane.getScene().setCursor(Cursor.HAND);
	}

	@FXML
	private void handleMinimizeImageViewMouseExited()
	{
		this.stackPane.getScene().setCursor(Cursor.DEFAULT);
	}

	@Override
	public abstract void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle);

	@PostConstruct
	public abstract void setupGui();

	public Stage getStage()
	{
		return this.stage;
	}

	public void setStage(Stage stage)
	{
		this.stage = stage;
	}

	public boolean isInitialized()
	{
		return this.initialized;
	}

	public void setInitialized(boolean initialized)
	{
		this.initialized = initialized;
	}

	protected StackPane getStackPane()
	{
		return this.stackPane;
	}
}
