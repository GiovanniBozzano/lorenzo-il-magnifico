package it.polimi.ingsw.lim.server.gui;

import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ControllerMain
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

	public Label getConnectionLabel()
	{
		return this.connectionLabel;
	}

	public TextArea getLogTextArea()
	{
		return this.logTextArea;
	}
}
