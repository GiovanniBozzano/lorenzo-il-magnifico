package it.polimi.ingsw.lim.server.gui;

import it.polimi.ingsw.lim.server.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
		this.startButton.getScene().getRoot().setDisable(true);
		Server.getInstance().start((Stage) this.startButton.getScene().getWindow(), Integer.parseInt(this.rmiPortTextField.getText()), Integer.parseInt(this.socketPortTextField.getText()));
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
	}
}
