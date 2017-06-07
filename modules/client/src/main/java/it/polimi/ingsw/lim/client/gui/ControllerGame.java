package it.polimi.ingsw.lim.client.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXTabPane;
import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.gui.CustomController;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGame extends CustomController
{
	@FXML private Pane gameBoard;
	@FXML private Pane playerBoard1DevelopmentCardsVenture;
	@FXML private Pane playerBoard2DevelopmentCardsVenture;
	@FXML private Pane playerBoard3DevelopmentCardsVenture;
	@FXML private Pane playerBoard4DevelopmentCardsVenture;
	@FXML private Pane playerBoard5DevelopmentCardsVenture;
	@FXML private Pane playerBoard1DevelopmentCardsCharacter;
	@FXML private Pane playerBoard2DevelopmentCardsCharacter;
	@FXML private Pane playerBoard3DevelopmentCardsCharacter;
	@FXML private Pane playerBoard4DevelopmentCardsCharacter;
	@FXML private Pane playerBoard5DevelopmentCardsCharacter;
	@FXML private Pane playerBoard1;
	@FXML private Pane playerBoard2;
	@FXML private Pane playerBoard3;
	@FXML private Pane playerBoard4;
	@FXML private Pane playerBoard5;
	@FXML private VBox rightVBox;
	@FXML private JFXTabPane playerTabPanel;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resourceBundle)
	{
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
		double originalGameBoardWidth = this.gameBoard.getWidth();
		double originalGameBoardHeight = this.gameBoard.getHeight();
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		double ratio = (bounds.getHeight() - bounds.getHeight() / 15) / this.getStackPane().getScene().getWindow().getHeight();
		this.getStackPane().getScene().getWindow().setWidth(this.getStackPane().getWidth() * ratio);
		this.getStackPane().getScene().getWindow().setHeight(this.getStackPane().getHeight() * ratio);
		this.getStackPane().getScene().getWindow().setX(bounds.getWidth() / 2 - this.getStackPane().getScene().getWindow().getWidth() / 2);
		this.getStackPane().getScene().getWindow().setY(bounds.getHeight() / 2 - this.getStackPane().getScene().getWindow().getHeight() / 2);
		this.getStackPane().setPrefWidth(this.getStackPane().getScene().getWindow().getWidth());
		this.getStackPane().setPrefHeight(this.getStackPane().getScene().getWindow().getHeight());
		this.getStackPane().getStylesheets().add(Client.getInstance().getClass().getResource("/css/jfoenix-nodes-list-button.css").toExternalForm());
		JFXButton button1 = new JFXButton("R1");
		button1.setButtonType(ButtonType.RAISED);
		button1.getStyleClass().addAll("animated-option-button");
		JFXButton button2 = new JFXButton("R2");
		button2.setButtonType(ButtonType.RAISED);
		button2.getStyleClass().addAll("animated-option-button");
		JFXButton button3 = new JFXButton("R3");
		button3.setButtonType(ButtonType.RAISED);
		button3.getStyleClass().addAll("animated-option-button");
		JFXNodesList nodesList = new JFXNodesList();
		nodesList.addAnimatedNode(button1);
		nodesList.addAnimatedNode(button2);
		nodesList.addAnimatedNode(button3);
		this.rightVBox.getChildren().add(nodesList);
		this.getStackPane().getScene().getWindow().sizeToScene();
		double gameBoardWidthRatio = this.gameBoard.getWidth() / originalGameBoardWidth;
		double gameBoardHeightRatio = this.gameBoard.getHeight() / originalGameBoardHeight;
		DropShadow borderGlow = new DropShadow();
		borderGlow.setOffsetY(0.0D);
		borderGlow.setOffsetX(0.0D);
		borderGlow.setColor(Color.WHITE);
		borderGlow.setWidth(40.0D);
		borderGlow.setHeight(40.0D);
		for (Node node : this.gameBoard.getChildren()) {
			if (node instanceof Pane) {
				((Pane) node).setSnapToPixel(false);
				((Pane) node).setPrefWidth(((Pane) node).getPrefWidth() * ratio);
				((Pane) node).setPrefHeight(((Pane) node).getPrefHeight() * ratio);
				node.setOnMouseEntered((event) -> node.setEffect(borderGlow));
				node.setOnMouseExited((event) -> node.effectProperty().set(null));
			} else if (node instanceof Label) {
				((Label) node).setSnapToPixel(false);
				((Label) node).setPrefWidth(((Label) node).getPrefWidth() * ratio);
				((Label) node).setPrefHeight(((Label) node).getPrefHeight() * ratio);
			}
			node.setLayoutX(node.getLayoutX() * gameBoardWidthRatio);
			node.setLayoutY(node.getLayoutY() * gameBoardHeightRatio);
		}
		double oldWidth = this.playerBoard1DevelopmentCardsVenture.getPrefWidth();
		this.playerBoard1DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard2DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard3DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard4DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard5DevelopmentCardsVenture.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard1DevelopmentCardsVenture.setPrefHeight(this.playerBoard1DevelopmentCardsVenture.getPrefHeight() * this.playerBoard1DevelopmentCardsVenture.getPrefWidth() / oldWidth);
		this.playerBoard2DevelopmentCardsVenture.setPrefHeight(this.playerBoard2DevelopmentCardsVenture.getPrefHeight() * this.playerBoard2DevelopmentCardsVenture.getPrefWidth() / oldWidth);
		this.playerBoard3DevelopmentCardsVenture.setPrefHeight(this.playerBoard3DevelopmentCardsVenture.getPrefHeight() * this.playerBoard3DevelopmentCardsVenture.getPrefWidth() / oldWidth);
		this.playerBoard4DevelopmentCardsVenture.setPrefHeight(this.playerBoard4DevelopmentCardsVenture.getPrefHeight() * this.playerBoard4DevelopmentCardsVenture.getPrefWidth() / oldWidth);
		this.playerBoard5DevelopmentCardsVenture.setPrefHeight(this.playerBoard5DevelopmentCardsVenture.getPrefHeight() * this.playerBoard5DevelopmentCardsVenture.getPrefWidth() / oldWidth);
		oldWidth = this.playerBoard1DevelopmentCardsCharacter.getPrefWidth();
		this.playerBoard1DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard2DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard3DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard4DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard5DevelopmentCardsCharacter.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard1DevelopmentCardsCharacter.setPrefHeight(this.playerBoard1DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard1DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard2DevelopmentCardsCharacter.setPrefHeight(this.playerBoard2DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard2DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard3DevelopmentCardsCharacter.setPrefHeight(this.playerBoard3DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard3DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard4DevelopmentCardsCharacter.setPrefHeight(this.playerBoard4DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard4DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		this.playerBoard5DevelopmentCardsCharacter.setPrefHeight(this.playerBoard5DevelopmentCardsCharacter.getPrefHeight() * this.playerBoard5DevelopmentCardsCharacter.getPrefWidth() / oldWidth);
		oldWidth = this.playerBoard1.getPrefWidth();
		this.playerBoard1.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard2.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard3.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard4.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard5.setPrefWidth(this.playerTabPanel.getWidth());
		this.playerBoard1.setPrefHeight(this.playerBoard1.getPrefHeight() * this.playerBoard1.getPrefWidth() / oldWidth);
		this.playerBoard2.setPrefHeight(this.playerBoard2.getPrefHeight() * this.playerBoard2.getPrefWidth() / oldWidth);
		this.playerBoard3.setPrefHeight(this.playerBoard3.getPrefHeight() * this.playerBoard3.getPrefWidth() / oldWidth);
		this.playerBoard4.setPrefHeight(this.playerBoard4.getPrefHeight() * this.playerBoard4.getPrefWidth() / oldWidth);
		this.playerBoard5.setPrefHeight(this.playerBoard5.getPrefHeight() * this.playerBoard5.getPrefWidth() / oldWidth);
		this.playerTabPanel.setMaxHeight(((VBox) this.playerBoard1.getParent()).getHeight());
	}
}
