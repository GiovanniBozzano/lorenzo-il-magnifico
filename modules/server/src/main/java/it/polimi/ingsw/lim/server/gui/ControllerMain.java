package it.polimi.ingsw.lim.server.gui;

import it.polimi.ingsw.lim.common.gui.CustomController;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain extends CustomController
{
	@FXML private Label connectionLabel;
	@FXML private TextArea logTextArea;

	@FXML
	private void handleCommandTextFieldAction(ActionEvent event)
	{
		String command = ((TextField) event.getSource()).getText().replace(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		((TextField) event.getSource()).clear();
		Utils.executeCommand(command);
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
		((Stage) this.getStackPane().getScene().getWindow()).iconifiedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				this.getStackPane().getScene().setCursor(Cursor.HAND);
				this.getStackPane().getScene().setCursor(Cursor.DEFAULT);
			}
		});
		this.getStackPane().getScene().getRoot().requestFocus();
	}

	@Override
	public void showDialog(String message)
	{
	}

	public Label getConnectionLabel()
	{
		return this.connectionLabel;
	}

	public TextArea getLogTextArea()
	{
		return this.logTextArea;
	}
}
