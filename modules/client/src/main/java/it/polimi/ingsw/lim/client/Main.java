package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.utils.Utils;
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
		Client.setLogger(Logger.getLogger(Client.class.getSimpleName().toUpperCase()));
		Client.getLogger().setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		Client.getLogger().addHandler(consoleHandler);
		Instance.setInstance(new Client());
		Main.launch(args);
	}

	@Override
	public void start(Stage stage)
	{
		CommonUtils.setNewWindow(Utils.SCENE_CONNECTION, null);
	}

	@Override
	public void stop()
	{
		Client.getInstance().stop();
	}
}
