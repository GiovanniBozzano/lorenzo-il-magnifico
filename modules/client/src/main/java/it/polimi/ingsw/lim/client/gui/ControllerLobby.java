package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.common.utils.WindowInformations;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class ControllerLobby implements Initializable
{
	@FXML private Label connectionLabel;
	@FXML private Label usernameLabel;
	@FXML private ListView<RoomInformations> roomsListView;
	@FXML private ListView<String> playersListView;
	@FXML private Button enterRoomButton;
	@FXML private Button createRoomButton;
	@FXML private Button disconnectButton;

	@FXML
	private void handleRoomsListViewMouseClicked()
	{
		if (this.roomsListView.getSelectionModel().getSelectedItem() == null) {
			return;
		}
		this.playersListView.getItems().clear();
		this.playersListView.getItems().setAll(this.roomsListView.getSelectionModel().getSelectedItem().getPlayerNames());
	}

	@FXML
	private void handleEnterRoomButtonAction()
	{
		if (this.roomsListView.getSelectionModel().getSelectedItem() == null) {
			return;
		}
		Client.getInstance().getConnectionHandler().sendRoomEntry(this.roomsListView.getSelectionModel().getSelectedItem().getId());
	}

	@FXML
	private void handleCreateRoomButtonAction()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(Utils.SCENE_ROOM_CREATION));
		try {
			Parent parent = fxmlLoader.load();
			ControllerRoomCreation.setPreviousWindowInformations(Client.getInstance().getWindowInformations());
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.initOwner(Client.getInstance().getWindowInformations().getStage().getScene().getWindow());
			stage.setScene(new Scene(parent));
			stage.sizeToScene();
			stage.setOnCloseRequest(event -> {
				Client.getInstance().setWindowInformations(ControllerRoomCreation.getPreviousWindowInformations());
				Client.getInstance().getConnectionHandler().sendRequestRoomList();
			});
			Client.getInstance().setWindowInformations(new WindowInformations(fxmlLoader.getController(), stage));
			stage.show();
		} catch (IOException exception) {
			Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@FXML
	private void handleDisconnectButtonAction()
	{
		Client.getInstance().disconnect(false, false);
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.connectionLabel.setText(Client.getInstance().getConnectionType().name() + " - " + Client.getInstance().getIp() + ":" + Client.getInstance().getPort());
		this.usernameLabel.setText(Client.getInstance().getUsername());
		this.roomsListView.setCellFactory(param -> new ListCell<RoomInformations>()
		{
			@Override
			protected void updateItem(RoomInformations roomInformations, boolean empty)
			{
				super.updateItem(roomInformations, empty);
				if (empty || roomInformations == null || roomInformations.getName() == null) {
					this.setText(null);
				} else {
					this.setText(roomInformations.getName());
				}
			}
		});
		this.playersListView.setCellFactory(param -> new ListCell<String>()
		{
			@Override
			protected void updateItem(String playerName, boolean empty)
			{
				super.updateItem(playerName, empty);
				this.setDisable(true);
				if (empty || playerName == null) {
					this.setText(null);
				} else {
					this.setText(playerName);
				}
			}
		});
		this.enterRoomButton.prefWidthProperty().bind(((VBox) this.enterRoomButton.getParent()).widthProperty());
		this.createRoomButton.prefWidthProperty().bind(((VBox) this.createRoomButton.getParent()).widthProperty());
		this.disconnectButton.prefWidthProperty().bind(((VBox) this.disconnectButton.getParent()).widthProperty());
	}

	public ListView<RoomInformations> getRoomsListView()
	{
		return this.roomsListView;
	}

	public ListView<String> getPlayerListView()
	{
		return this.playersListView;
	}
}
