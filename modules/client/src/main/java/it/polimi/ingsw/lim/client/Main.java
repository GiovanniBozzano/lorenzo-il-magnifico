package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.cli.CLIListenerClient;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.LoggerFormatter;
import it.polimi.ingsw.lim.common.utils.WindowFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application
{
	private static String[] args;

	public static void main(String[] args)
	{
		Main.args = args;
		Client.setDebugger(Logger.getLogger(Client.class.getSimpleName().toUpperCase()));
		Client.getDebugger().setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new DebuggerFormatter());
		Client.getDebugger().addHandler(consoleHandler);
		Client.setLogger(Logger.getLogger("LOGGER"));
		Client.getLogger().setUseParentHandlers(false);
		consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LoggerFormatter());
		Client.getLogger().addHandler(consoleHandler);
		Instance.setInstance(new Client());
		Client.setCliListener(new CLIListenerClient());
		Client.getLogger().log(Level.INFO, "Enter Interface Type...");
		Client.getLogger().log(Level.INFO, "1 - GUI");
		Client.getLogger().log(Level.INFO, "2 - CLI");
		Client.getCliListener().start();
	}

	@Override
	public void start(Stage stage)
	{
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_CONNECTION, true, () -> Client.getCliListener().end());
		//WindowFactory.getInstance().setNewWindow("/fxml/SceneGame.fxml", true);
	}

	@Override
	public void stop()
	{
		Client.getInstance().stop();
	}

	public static String[] getArgs()
	{
		return Main.args;
	}
}
