package it.polimi.ingsw.lim.client;

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
		stage.setTitle("Client");
		stage.setResizable(false);
		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(25, 25, 25, 25));
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(8);
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		column1.setPercentWidth(25);
		column2.setPercentWidth(75);
		grid.getColumnConstraints().addAll(column1, column2);
		Label ipLabel = new Label("IP:");
		TextField ipTextField = new TextField("localhost");
		Label portLabel = new Label("Port:");
		TextField portTextField = new TextField("8080");
		Label nameLabel = new Label("Name:");
		TextField nameTextField = new TextField();
		grid.add(ipLabel, 0, 0);
		grid.add(ipTextField, 1, 0);
		grid.add(portLabel, 0, 1);
		grid.add(portTextField, 1, 1);
		grid.add(nameLabel, 0, 2);
		grid.add(nameTextField, 1, 2);
		HBox hBox = new HBox();
		Button startButton = new Button("CONNECT");
		startButton.setOnAction((ActionEvent event) -> new Client(stage, ipTextField.getText(), Integer.parseInt(portTextField.getText()), nameTextField.getText()));
		startButton.setMaxWidth(Double.MAX_VALUE);
		hBox.getChildren().add(startButton);
		HBox.setHgrow(startButton, Priority.ALWAYS);
		vBox.getChildren().add(grid);
		vBox.getChildren().add(hBox);
		stage.setScene(new Scene(vBox, 200, 160));
		stage.show();
	}

	@Override
	public void stop()
	{
		if (Client.getInstance() != null) {
			Client.getInstance().stop();
		}
	}
}
