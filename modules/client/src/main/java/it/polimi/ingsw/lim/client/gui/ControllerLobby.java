package it.polimi.ingsw.lim.client.gui;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLobby implements Initializable
{
	@FXML private Label connectionLabel;
	@FXML private Label nameLabel;
	@FXML private ListView<RoomInformations> roomsListView;
	@FXML private ListView<String> playersListView;

	@FXML
	private void handleRoomsListViewMouseClicked()
	{
		this.playersListView.getItems().setAll(this.roomsListView.getSelectionModel().getSelectedItem().getPlayerNames());
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.connectionLabel.setText(Client.getInstance().getConnectionType().name() + " - " + Client.getInstance().getIp() + ":" + Client.getInstance().getPort());
		this.nameLabel.setText(Client.getInstance().getName());
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
