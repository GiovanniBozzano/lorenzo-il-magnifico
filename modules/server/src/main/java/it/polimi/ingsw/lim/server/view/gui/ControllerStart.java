package it.polimi.ingsw.lim.server.view.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import it.polimi.ingsw.lim.common.view.gui.CustomController;
import it.polimi.ingsw.lim.server.Server;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStart extends CustomController
{
	@FXML private JFXTextField rmiPortTextField;
	@FXML private JFXTextField socketPortTextField;
	@FXML private JFXButton startButton;

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
		this.getStackPane().getScene().getRoot().requestFocus();
	}

	@FXML
	private void handleStartButtonAction()
	{
		this.getStage().getScene().getRoot().setDisable(true);
		Server.getInstance().setup(Integer.parseInt(this.rmiPortTextField.getText()), Integer.parseInt(this.socketPortTextField.getText()));
	}
}
