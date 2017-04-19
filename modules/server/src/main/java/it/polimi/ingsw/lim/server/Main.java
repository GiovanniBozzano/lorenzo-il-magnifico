package it.polimi.ingsw.lim.server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application
{
	public static void main(String[] args)
	{
		Main.launch(args);
	}

	@Override
	public void start(Stage stage)
	{
		stage.setTitle("Server");
		stage.setResizable(false);
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(25, 25, 25, 25));
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(20);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(80);
		grid.getColumnConstraints().addAll(column1, column2);
		Label portLabel = new Label("Port:");
		TextField portTextField = new TextField("8080");
		grid.add(portLabel, 0, 0);
		grid.add(portTextField, 1, 0);
		HBox hBox = new HBox();
		Button startButton = new Button("START");
		startButton.setOnAction((ActionEvent event) -> new Server(stage, Integer.parseInt(portTextField.getText())));
		startButton.setMaxWidth(Double.MAX_VALUE);
		hBox.getChildren().add(startButton);
		HBox.setHgrow(startButton, Priority.ALWAYS);
		vBox.getChildren().add(grid);
		vBox.getChildren().add(hBox);
		stage.setScene(new Scene(vBox, 200, 100));
		stage.show();
	}

	@Override
	public void stop()
	{
		if (Server.getInstance() != null) {
			Server.getInstance().stop();
		}
	}
}
