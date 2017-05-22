package it.polimi.ingsw.lim.client.gui;

import com.jfoenix.controls.JFXListView;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.gui.IController;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerRoom implements Initializable, IController
{
	@FXML private StackPane stackPane;
	@FXML private Label roomNameLabel;
	@FXML private TextArea chatTextArea;
	@FXML private JFXListView<String> rulesListView;
	@FXML private JFXListView<String> playersListView;
	private double xOffset;
	private double yOffset;

	@FXML
	private void handleStackPaneMousePressed(MouseEvent event)
	{
		this.stackPane.getScene().getRoot().requestFocus();
		this.xOffset = this.stackPane.getScene().getWindow().getX() - event.getScreenX();
		this.yOffset = this.stackPane.getScene().getWindow().getY() - event.getScreenY();
	}

	@FXML
	private void handleStackPaneMouseDragged(MouseEvent event)
	{
		this.stackPane.getScene().getWindow().setX(event.getScreenX() + this.xOffset);
		this.stackPane.getScene().getWindow().setY(event.getScreenY() + this.yOffset);
	}

	@FXML
	private void handleQuitImageViewMouseClicked()
	{
		Client.getInstance().stop();
	}

	@FXML
	private void handleQuitImageViewMouseEntered()
	{
		this.stackPane.getScene().setCursor(Cursor.HAND);
	}

	@FXML
	private void handleQuitImageViewMouseExited()
	{
		this.stackPane.getScene().setCursor(Cursor.DEFAULT);
	}

	@FXML
	private void handleMinimizeImageViewMouseClicked()
	{
		((Stage) this.stackPane.getScene().getWindow()).setIconified(true);
	}

	@FXML
	private void handleMinimizeImageViewMouseEntered()
	{
		this.stackPane.getScene().setCursor(Cursor.HAND);
	}

	@FXML
	private void handleMinimizeImageViewMouseExited()
	{
		this.stackPane.getScene().setCursor(Cursor.DEFAULT);
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

	@Override
	@PostConstruct
	public void setupGui()
	{
		((Stage) this.stackPane.getScene().getWindow()).iconifiedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				this.stackPane.getScene().setCursor(Cursor.HAND);
				this.stackPane.getScene().setCursor(Cursor.DEFAULT);
			}
		});
		this.stackPane.getScene().getRoot().requestFocus();
	}

	public void setRoomInformations(RoomType roomType, List<String> playerNames)
	{
		this.roomNameLabel.setText(roomType.name());
		this.playersListView.getItems().addAll(playerNames);
	}

	public ListView<String> getPlayersListView()
	{
		return this.playersListView;
	}

	public TextArea getChatTextArea()
	{
		return this.chatTextArea;
	}
}
