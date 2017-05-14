package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable
{
	@FXML private TextField usernameTextField;
	@FXML private TextField passwordTextField;
	@FXML private Button loginButton;
	@FXML private Button registerButton;

	@FXML
	public void handleLoginButtonAction()
	{
		String username = this.usernameTextField.getText().replaceAll("^\\s+|\\s+$", "");
		if (username.length() < 1) {
			this.usernameTextField.clear();
			this.usernameTextField.setPromptText("Insert a username");
			return;
		}
		if (!username.matches("^[\\w\\-]{4,16}$")) {
			this.usernameTextField.clear();
			this.usernameTextField.setPromptText("Insert a valid username");
			return;
		}
		Connection.sendLogin(username, CommonUtils.encrypt(this.usernameTextField.getText()));
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.loginButton.prefWidthProperty().bind(((VBox) this.loginButton.getParent()).widthProperty());
		this.registerButton.prefWidthProperty().bind(((VBox) this.registerButton.getParent()).widthProperty());
	}
}
