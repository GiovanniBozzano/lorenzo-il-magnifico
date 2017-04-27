package it.polimi.ingsw.lim.client;

import javafx.application.Application;
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
		Client.getInstance().setNewWindow("/fxml/SceneConnection.fxml");
	}

	@Override
	public void stop()
	{
		Client.getInstance().stop();
	}
}
