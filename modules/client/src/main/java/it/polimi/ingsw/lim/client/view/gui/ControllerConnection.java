package it.polimi.ingsw.lim.client.view.gui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.ConnectionType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.view.gui.CustomController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerConnection extends CustomController
{
	@FXML private JFXTextField ipTextField;
	@FXML private JFXTextField portTextField;
	@FXML private JFXRadioButton rmiRadioButton;
	@FXML private JFXRadioButton socketRadioButton;
	@FXML private JFXButton connectionButton;
	@FXML private JFXDialog dialog;
	@FXML private JFXDialogLayout dialogLayout;
	@FXML private Label dialogLabel;
	@FXML private JFXButton dialogOkButton;

	@FXML
	private void handleConnectionButtonAction()
	{
		String ip = this.ipTextField.getText().replace(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		String port = this.portTextField.getText().replace(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
		if (!CommonUtils.isInteger(port)) {
			this.portTextField.clear();
			this.portTextField.setPromptText("Insert a valid port");
			return;
		}
		this.getStage().getScene().getRoot().setDisable(true);
		Client.getInstance().setup(this.rmiRadioButton.isSelected() ? ConnectionType.RMI : ConnectionType.SOCKET, ip, Integer.parseInt(port));
	}

	@FXML
	private void handleRmiRadioButtonAction()
	{
		this.portTextField.setPromptText("Enter port (default: 8080)...");
	}

	@FXML
	private void handleSocketRadioButtonAction()
	{
		this.portTextField.setPromptText("Enter port (default: 8081)...");
	}

	@FXML
	public void handleDialogOkButtonAction()
	{
		this.dialog.close();
		this.getStackPane().getScene().getRoot().requestFocus();
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
		this.getStackPane().getChildren().remove(this.dialog);
		this.dialog.setTransitionType(DialogTransition.CENTER);
		this.dialog.setDialogContainer(this.getStackPane());
		this.portTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				this.portTextField.setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		this.connectionButton.disableProperty().bind((this.ipTextField.textProperty().isNotEmpty().and(this.portTextField.textProperty().isNotEmpty()).and(this.rmiRadioButton.selectedProperty().or(this.socketRadioButton.selectedProperty()))).not());
	}

	@Override
	@PostConstruct
	public void setupGui()
	{
		this.getStage().show();
		this.getStackPane().getScene().getRoot().requestFocus();
		((StackPane) ((JFXRippler) ((AnchorPane) this.rmiRadioButton.getChildrenUnmodifiable().get(1)).getChildren().get(0)).getChildren().get(0)).setPadding(new Insets(0.0D));
		this.rmiRadioButton.getChildrenUnmodifiable().get(0).setTranslateX(10.0D);
		((StackPane) ((JFXRippler) ((AnchorPane) this.socketRadioButton.getChildrenUnmodifiable().get(1)).getChildren().get(0)).getChildren().get(0)).setPadding(new Insets(0.0D));
		this.socketRadioButton.getChildrenUnmodifiable().get(0).setTranslateX(10.0D);
		this.connectionButton.setPrefWidth(((VBox) this.connectionButton.getParent()).getWidth());
		this.dialogLayout.setMinWidth(this.dialog.getWidth() - 20.0D);
		this.dialogLayout.setPrefWidth(this.dialog.getWidth() - 20.0D);
		this.dialogLayout.setMaxSize(this.dialog.getWidth() - 20.0D, this.dialog.getHeight() - 20.0D);
		this.dialogOkButton.setPrefWidth(((VBox) this.dialogOkButton.getParent()).getWidth());
	}

	void showDialog(String message)
	{
		this.dialogLabel.setText(message);
		this.dialog.show();
	}
}
