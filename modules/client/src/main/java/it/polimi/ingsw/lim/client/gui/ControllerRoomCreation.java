package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.utils.WindowInformations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRoomCreation implements Initializable
{
	private WindowInformations previousWindowInformations;
	@FXML private TextField nameTextField;

	@FXML
	private void buttonCreateAction()
	{
		if (this.nameTextField.getText().length() < 1) {
			this.nameTextField.setPromptText("Insert a name");
			return;
		}
		Client.getInstance().sendRoomCreation(this.nameTextField.getText());
	}

	@FXML
	private void buttonCancelAction()
	{
		((Stage) this.nameTextField.getScene().getWindow()).close();
		Client.getInstance().setWindowInformations(this.previousWindowInformations);
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		Client.getInstance().getWindowInformations().getStage().setOnCloseRequest(event -> Client.getInstance().setWindowInformations(this.previousWindowInformations));
	}

	void setPreviousWindowInformations(WindowInformations windowInformations)
	{
		this.previousWindowInformations = windowInformations;
	}
}
