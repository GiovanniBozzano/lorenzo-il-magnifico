package it.polimi.ingsw.lim.server.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import it.polimi.ingsw.lim.common.gui.CustomController;
import it.polimi.ingsw.lim.server.Server;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStart extends CustomController
{
	@FXML private JFXTextField rmiPortTextField;
	@FXML private JFXTextField socketPortTextField;
	@FXML private JFXButton startButton;

	@FXML
	private void handleStartButtonAction()
	{
		this.setDisable(true);
		Server.getInstance().setup(Integer.parseInt(this.rmiPortTextField.getText()), Integer.parseInt(this.socketPortTextField.getText()));
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.startButton.prefWidthProperty().bind(((VBox) this.startButton.getParent()).widthProperty());
		this.rmiPortTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				this.rmiPortTextField.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		this.socketPortTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				this.socketPortTextField.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		this.startButton.disableProperty().bind((this.rmiPortTextField.textProperty().isNotEmpty().and(this.socketPortTextField.textProperty().isNotEmpty())).not());
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
		((Stage) this.getStackPane().getScene().getWindow()).iconifiedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				this.getStackPane().getScene().setCursor(Cursor.HAND);
				this.getStackPane().getScene().setCursor(Cursor.DEFAULT);
			}
		});
		this.getStackPane().getScene().getRoot().requestFocus();
	}
}
