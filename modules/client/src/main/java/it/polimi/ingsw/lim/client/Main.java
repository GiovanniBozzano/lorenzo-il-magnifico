package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.view.cli.InterfaceHandlerCLI;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.common.utils.LoggerFormatter;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main extends Application
{
	private static String[] args;
	private static Application application;

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
		Client.getInstance().setInterfaceHandler(new InterfaceHandlerCLI());
		Client.getInstance().getInterfaceHandler().start();
	}

	@SuppressWarnings("squid:S2696")
	@Override
	public void start(Stage stage)
	{
		Main.application = this;
		Client.getInstance().getInterfaceHandler().start(stage);
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

	public static Application getApplication()
	{
		return Main.application;
	}
}
