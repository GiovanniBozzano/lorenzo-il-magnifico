package it.polimi.ingsw.lim.server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application
{
	public static void main(String[] args)
	{
		Main.launch(args);
	}

	public void start(Stage stage)
	{
		stage.setTitle("Server");
		Button start = new Button("START");
		start.setOnAction((ActionEvent event) -> new Server());
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(start);
		stage.setScene(new Scene(stackPane, 300, 250));
		stage.show();
	}
}
