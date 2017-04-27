package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerConnection implements Initializable
{
	@FXML private TextField ipTextField;
	@FXML private TextField portTextField;
	@FXML private TextField nameTextField;
	@FXML private RadioButton rmiRadioButton;
	@FXML private Button connectionButton;

	@FXML
	private void handleConnectionButtonAction()
	{
		this.connectionButton.getScene().getRoot().setDisable(true);
		Client.getInstance().connect(this.rmiRadioButton.isSelected() ? ConnectionType.RMI : ConnectionType.SOCKET, this.ipTextField.getText(), Integer.parseInt(this.portTextField.getText()), this.nameTextField.getText());
	}

	@FXML
	private void handleRmiRadioButtonAction()
	{
		this.portTextField.setText("8080");
	}

	@FXML
	private void handleSocketRadioButtonAction()
	{
		this.portTextField.setText("8081");
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
	}
}
