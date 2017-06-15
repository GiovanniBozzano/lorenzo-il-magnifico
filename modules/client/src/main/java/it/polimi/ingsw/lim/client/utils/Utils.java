package it.polimi.ingsw.lim.client.utils;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;

public class Utils
{
	public static final String SCENE_AUTHENTICATION = "/fxml/SceneAuthentication.fxml";
	public static final String SCENE_CONNECTION = "/fxml/SceneConnection.fxml";
	public static final String SCENE_ROOM = "/fxml/SceneRoom.fxml";
	public static final String SCENE_GAME = "/fxml/SceneGame.fxml";

	private Utils()
	{
	}

	public static void resizeChildrenNode(Pane pane, double widthRatio, double heightRatio)
	{
		for (Node child : pane.getChildren()) {
			if (child instanceof Pane) {
				((Pane) child).setSnapToPixel(false);
				((Pane) child).setPrefWidth(((Pane) child).getPrefWidth() * widthRatio);
				((Pane) child).setPrefHeight(((Pane) child).getPrefHeight() * heightRatio);
			} else if (child instanceof Label) {
				((Label) child).setSnapToPixel(false);
				((Label) child).setPrefWidth(((Label) child).getPrefWidth() * widthRatio);
				((Label) child).setPrefHeight(((Label) child).getPrefHeight() * heightRatio);
			}
			child.setLayoutX(child.getLayoutX() * widthRatio);
			child.setLayoutY(child.getLayoutY() * heightRatio);
		}
	}

	public static void setEffect(Pane pane, Effect effect)
	{
		pane.setOnMouseEntered(event -> pane.setEffect(effect));
		pane.setOnMouseExited(event -> pane.effectProperty().set(null));
	}
}
