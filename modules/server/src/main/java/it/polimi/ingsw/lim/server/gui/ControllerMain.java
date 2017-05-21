package it.polimi.ingsw.lim.server.gui;

import it.polimi.ingsw.lim.common.gui.IController;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable, IController
{
	@FXML private StackPane stackPane;
	@FXML private Label connectionLabel;
	@FXML private TextArea logTextArea;
	private double xOffset;
	private double yOffset;

	@FXML
	private void handleCommandTextFieldAction(ActionEvent event)
	{
		String command = ((TextField) event.getSource()).getText().replace(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		((TextField) event.getSource()).clear();
		Utils.executeCommand(command);
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.stackPane.setOnMousePressed(event -> {
			this.xOffset = this.stackPane.getScene().getWindow().getX() - event.getScreenX();
			this.yOffset = this.stackPane.getScene().getWindow().getY() - event.getScreenY();
		});
		this.stackPane.setOnMouseDragged(event -> {
			this.stackPane.getScene().getWindow().setX(event.getScreenX() + this.xOffset);
			this.stackPane.getScene().getWindow().setY(event.getScreenY() + this.yOffset);
		});
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
	}

	public Label getConnectionLabel()
	{
		return this.connectionLabel;
	}

	public TextArea getLogTextArea()
	{
		return this.logTextArea;
	}
}
