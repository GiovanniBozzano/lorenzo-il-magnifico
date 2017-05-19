package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerAuthentication implements Initializable
{
	@FXML private TextField usernameTextField;
	@FXML private PasswordField passwordTextField;
	@FXML private Button loginButton;
	@FXML private Button registerButton;

	@FXML
	public void handleLoginButtonAction()
	{
		String username = this.usernameTextField.getText().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (!username.matches(CommonUtils.REGEX_USERNAME)) {
			this.usernameTextField.clear();
			this.usernameTextField.setPromptText("Insert a valid username");
			return;
		}
		if (this.passwordTextField.getText().length() < 1) {
			this.passwordTextField.clear();
			this.passwordTextField.setPromptText("Insert a password");
			return;
		}
		Client.getInstance().getConnectionHandler().sendLogin(username, this.passwordTextField.getText());
	}

	@FXML
	public void handleRegisterButtonAction()
	{
		String username = this.usernameTextField.getText().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (!username.matches(CommonUtils.REGEX_USERNAME)) {
			this.usernameTextField.clear();
			this.usernameTextField.setPromptText("Insert a valid username");
			return;
		}
		if (this.passwordTextField.getText().length() < 1) {
			this.passwordTextField.clear();
			this.passwordTextField.setPromptText("Insert a password");
			return;
		}
		Client.getInstance().getConnectionHandler().sendRegistration(username, this.passwordTextField.getText());
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.loginButton.prefWidthProperty().bind(((VBox) this.loginButton.getParent()).widthProperty());
		this.registerButton.prefWidthProperty().bind(((VBox) this.registerButton.getParent()).widthProperty());
	}
}
