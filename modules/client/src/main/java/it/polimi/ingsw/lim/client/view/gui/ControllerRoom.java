package it.polimi.ingsw.lim.client.view.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.Main;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.view.gui.CustomController;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

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
		HostServices hostServices = Main.getApplication().getHostServices();
		try {
			File file = new File(new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/Lorenzo il Magnifico Rulebook.pdf");
			if (file.exists()) {
				hostServices.showDocument(file.getAbsolutePath());
			} else {
				hostServices.showDocument(CommonUtils.exportResource("/guide.pdf", "/Lorenzo il Magnifico Rulebook.pdf"));
			}
		} catch (URISyntaxException | IOException exception) {
			Client.getDebugger().log(Level.SEVERE, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@FXML
	private void handleChatTextAreaAction(ActionEvent event)
	{
		Utils.sendChatMessage((TextField) event.getSource(), this.chatTextArea);
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

	void setRoomInformations(RoomType roomType, List<String> playerNames)
	{
		this.playerNameLabel.setText(Client.getInstance().getUsername());
		this.rulesListView.getItems().add("Room type: " + roomType.getDisplayName());
		if (roomType == RoomType.EXTENDED) {
			this.playersListView.setPrefHeight(122.0D);
			this.getStackPane().getScene().getWindow().sizeToScene();
		}
		this.playersListView.getItems().addAll(playerNames);
		if (playerNames.size() < 2 || roomType == RoomType.EXTENDED) {
			this.timerLabel.setText("Waiting for other players...");
		} else {
			Client.getInstance().getConnectionHandler().sendRoomTimerRequest();
		}
	}

	ListView<String> getPlayersListView()
	{
		return this.playersListView;
	}

	TextArea getChatTextArea()
	{
		return this.chatTextArea;
	}

	Label getTimerLabel()
	{
		return this.timerLabel;
	}
}
