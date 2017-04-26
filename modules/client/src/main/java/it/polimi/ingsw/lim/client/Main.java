package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.common.utils.LogFormatter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

public class Main extends Application
{
	public static void main(String[] args)
	{
		Main.launch(args);
	}

	@Override
	public void start(Stage stage)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneConnection.fxml"));
			stage.setScene(new Scene(root));
			stage.sizeToScene();
			stage.setResizable(false);
			stage.show();
		} catch (IOException exception) {
			Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void stop()
	{
		if (Client.getInstance() != null) {
			Client.getInstance().stop();
		}
	}
}
