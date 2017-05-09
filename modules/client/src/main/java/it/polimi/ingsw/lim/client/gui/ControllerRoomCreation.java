package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.common.utils.WindowInformations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRoomCreation implements Initializable
{
	private static WindowInformations previousWindowInformations;
	@FXML private TextField nameTextField;
	@FXML private Button createButton;
	@FXML private Button cancelButton;

	@FXML
	private void handleCreateButtonAction()
	{
		String name = this.nameTextField.getText().replaceAll("^\\s+|\\s+$", "");
		if (name.length() < 1) {
			this.nameTextField.clear();
			this.nameTextField.setPromptText("Insert a name");
			return;
		}
		if (!name.matches("^[\\w\\-\\s]{1,16}$")) {
			this.nameTextField.clear();
			this.nameTextField.setPromptText("Insert a valid name");
			return;
		}
		Connection.sendRoomCreation(name);
	}

	@FXML
	private void handleCancelButtonAction()
	{
		Client.getInstance().getWindowInformations().getStage().close();
		Client.getInstance().setWindowInformations(ControllerRoomCreation.previousWindowInformations);
		Connection.sendRequestRoomList();
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.createButton.prefWidthProperty().bind(((GridPane) this.createButton.getParent()).widthProperty().subtract(5).divide(2));
		this.cancelButton.prefWidthProperty().bind(((GridPane) this.cancelButton.getParent()).widthProperty().subtract(5).divide(2));
	}

	public void close()
	{
		Client.getInstance().getWindowInformations().getStage().close();
		Client.getInstance().setWindowInformations(ControllerRoomCreation.getPreviousWindowInformations());
	}

	static WindowInformations getPreviousWindowInformations()
	{
		return ControllerRoomCreation.previousWindowInformations;
	}

	static void setPreviousWindowInformations(WindowInformations windowInformations)
	{
		ControllerRoomCreation.previousWindowInformations = new WindowInformations(windowInformations.getController(), windowInformations.getStage());
	}

	public TextField getNameTextField()
	{
		return this.nameTextField;
	}
}
