package it.polimi.ingsw.lim.client;

import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
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
		Client.getInstance().getCliListener().execute(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					ICLIHandler cliHandler = Client.getCliHandlers().get(Client.getInstance().getCliStatus());
					Client.getInstance().setCurrentCliHandler(cliHandler);
					cliHandler.execute();
				} catch (IllegalStateException exception) {
					Client.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
					Thread.currentThread().interrupt();
				}
			}
		});
	}

	@Override
	public void start(Stage stage)
	{
		WindowFactory.getInstance().setNewWindow(Utils.SCENE_CONNECTION, () -> {
			Client.getInstance().getCliScanner().close();
			Client.getInstance().setCliStatus(CLIStatus.NONE);
			Client.getInstance().getCliListener().shutdownNow();
		});
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
