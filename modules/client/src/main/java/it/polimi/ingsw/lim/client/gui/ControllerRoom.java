package it.polimi.ingsw.lim.client.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.gui.CustomController;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerRoom extends CustomController
{
	@FXML private Label playerNameLabel;
	@FXML private TextArea chatTextArea;
	@FXML private JFXButton gameRulesButton;
	@FXML private JFXListView<String> rulesListView;
	@FXML private JFXListView<String> playersListView;
	@FXML private Label timerLabel;

	@FXML
	private void handleGameRulesButtonAction()
	{
	}

	@FXML
	private void handleChatTextAreaAction(ActionEvent event)
	{
		String text = ((TextField) event.getSource()).getText().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (text.length() < 1) {
			return;
		}
		((TextField) event.getSource()).clear();
		Client.getInstance().getConnectionHandler().sendChatMessage(text);
		if (this.chatTextArea.getText().length() < 1) {
			this.chatTextArea.appendText("[ME]: " + text);
		} else {
			this.chatTextArea.appendText("\n[ME]: " + text);
		}
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.rulesListView.setCellFactory(parameter -> new ListCell<String>()
		{
			@Override
			protected void updateItem(String rule, boolean empty)
			{
				super.updateItem(rule, empty);
				super.setFont(Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-Regular.ttf"), 12));
				this.setDisable(true);
				if (empty || rule == null) {
					this.setText(null);
				} else {
					this.setText(rule);
				}
			}
		});
		this.playersListView.setCellFactory(parameter -> new ListCell<String>()
		{
			@Override
			protected void updateItem(String playerName, boolean empty)
			{
				super.updateItem(playerName, empty);
				super.setFont(Font.loadFont(this.getClass().getResourceAsStream("/font/roboto/Roboto-Regular.ttf"), 12));
				this.setDisable(true);
				if (empty || playerName == null) {
					this.setText(null);
				} else {
					this.setText(playerName);
				}
			}
		});
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
		this.getStackPane().getScene().getRoot().requestFocus();
		this.gameRulesButton.setPrefWidth(((VBox) this.gameRulesButton.getParent()).getWidth());
	}

	@Override
	public void showDialog(String message)
	{
	}

	public void setRoomInformations(RoomType roomType, List<String> playerNames)
	{
		this.playerNameLabel.setText(Client.getInstance().getUsername());
		this.rulesListView.getItems().add("Room type: " + roomType.getDisplayName());
		if (roomType == RoomType.EXTENDED) {
			this.playersListView.setPrefHeight(122.0D);
			this.getStackPane().getScene().getWindow().sizeToScene();
		}
		this.playersListView.getItems().addAll(playerNames);
		if (playerNames.size() < 2) {
			this.timerLabel.setText("Waiting for other players...");
		} else {
			Client.getInstance().getConnectionHandler().sendRoomTimerRequest();
		}
	}

	public ListView<String> getPlayersListView()
	{
		return this.playersListView;
	}

	public TextArea getChatTextArea()
	{
		return this.chatTextArea;
	}

	public Label getTimerLabel()
	{
		return this.timerLabel;
	}
}
