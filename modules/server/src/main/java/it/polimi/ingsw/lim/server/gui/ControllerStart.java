package it.polimi.ingsw.lim.server.gui;

import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStart implements Initializable
{
	@FXML private TextField rmiPortTextField;
	@FXML private TextField socketPortTextField;
	@FXML private Button startButton;

	@FXML
	private void handleStartButtonAction()
	{
		String rmiPort = this.rmiPortTextField.getText().replace("^\\s+|\\s+$", "");
		String socketPort = this.socketPortTextField.getText().replace("^\\s+|\\s+$", "");
		if (rmiPort.length() < 1) {
			this.rmiPortTextField.clear();
			this.rmiPortTextField.setPromptText("Insert a RMI port");
			return;
		}
		if (!CommonUtils.isInteger(rmiPort)) {
			this.rmiPortTextField.clear();
			this.rmiPortTextField.setPromptText("Insert a valid RMI port");
			return;
		}
		if (socketPort.length() < 1) {
			this.socketPortTextField.clear();
			this.socketPortTextField.setPromptText("Insert a socket port");
			return;
		}
		if (!CommonUtils.isInteger(socketPort)) {
			this.socketPortTextField.clear();
			this.socketPortTextField.setPromptText("Insert a valid socket port");
			return;
		}
		this.startButton.getScene().getRoot().setDisable(true);
		Server.getInstance().setup(Integer.parseInt(this.rmiPortTextField.getText()), Integer.parseInt(this.socketPortTextField.getText()));
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.startButton.prefWidthProperty().bind(((VBox) this.startButton.getParent()).widthProperty());
	}
}
