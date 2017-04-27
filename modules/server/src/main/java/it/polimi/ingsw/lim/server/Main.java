package it.polimi.ingsw.lim.server;

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
		Server.getInstance().setNewWindow("/fxml/SceneStart.fxml");
	}

	@Override
	public void stop()
	{
		Server.getInstance().stop();
	}
}
