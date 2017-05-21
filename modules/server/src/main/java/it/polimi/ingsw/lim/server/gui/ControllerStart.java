package it.polimi.ingsw.lim.server.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import it.polimi.ingsw.lim.common.gui.IController;
import it.polimi.ingsw.lim.server.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerStart implements Initializable, IController
{
	@FXML private JFXTextField rmiPortTextField;
	@FXML private JFXTextField socketPortTextField;
	@FXML private JFXButton startButton;

	@FXML
	private void handleStartButtonAction()
	{
		this.startButton.getScene().getRoot().setDisable(true);
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
	}
}
