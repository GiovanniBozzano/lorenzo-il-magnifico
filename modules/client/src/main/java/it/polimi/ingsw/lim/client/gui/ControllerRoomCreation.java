package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.WindowInformations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
		String name = this.nameTextField.getText().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
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
		this.createButton.prefWidthProperty().bind(((VBox) this.createButton.getParent()).widthProperty());
		this.cancelButton.prefWidthProperty().bind(((VBox) this.cancelButton.getParent()).widthProperty());
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
