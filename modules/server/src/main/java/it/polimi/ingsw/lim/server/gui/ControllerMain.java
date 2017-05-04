package it.polimi.ingsw.lim.server.gui;

import it.polimi.ingsw.lim.server.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable
{
	@FXML private TextArea logTextArea;

	@FXML
	private void handleCommandTextFieldAction(ActionEvent event)
	{
		String command = ((TextField) event.getSource()).getText().replace("^\\s+|\\s+$", "");
		((TextField) event.getSource()).clear();
		Utils.executeCommand(command);
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
	}

	public TextArea getLogTextArea()
	{
		return this.logTextArea;
	}
}
