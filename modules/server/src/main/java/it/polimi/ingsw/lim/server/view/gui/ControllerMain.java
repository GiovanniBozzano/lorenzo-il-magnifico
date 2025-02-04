package it.polimi.ingsw.lim.server.view.gui;

import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.view.gui.CustomController;
import it.polimi.ingsw.lim.server.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain extends CustomController
{
	@FXML private Label connectionLabel;
	@FXML private TextArea logTextArea;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		// This method is empty because it is not implemented by this controller.
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
		this.getStackPane().getScene().getRoot().requestFocus();
	}

	@FXML
	private void handleCommandTextFieldAction(ActionEvent event)
	{
		String command = ((TextField) event.getSource()).getText().replace(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		((TextField) event.getSource()).clear();
		Utils.executeCommand(command);
	}

	Label getConnectionLabel()
	{
		return this.connectionLabel;
	}

	TextArea getLogTextArea()
	{
		return this.logTextArea;
	}
}
