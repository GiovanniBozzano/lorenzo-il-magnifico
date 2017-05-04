package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main extends Application
{
	public static void main(String[] args)
	{
		Server.setLogger(Logger.getLogger(Server.class.getSimpleName().toUpperCase()));
		Server.getLogger().setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		Server.getLogger().addHandler(consoleHandler);
		Instance.setInstance(new Server());
		Main.launch(args);
	}

	@Override
	public void start(Stage stage)
	{
		CommonUtils.setNewWindow("/fxml/SceneStart.fxml", null);
	}

	@Override
	public void stop()
	{
		Server.getInstance().stop();
	}
}
