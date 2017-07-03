package it.polimi.ingsw.lim.server.view.cli;

import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.enums.CLIStatus;
import it.polimi.ingsw.lim.server.view.IInterfaceHandler;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.logging.Level;

public class InterfaceHandlerCLI implements IInterfaceHandler
{
	@Override
	public void start()
	{
		Server.getInstance().getCliListener().execute(() -> Server.getCliHandlers().get(Server.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void start(Stage stage)
	{
		// This method is empty because it is not implemented by the CLI.
	}

	@Override
	public void stop()
	{
		Server.getInstance().getCliScanner().close();
		Server.getInstance().getCliListener().shutdownNow();
	}

	@Override
	public void setupSuccess(int rmiPort, int socketPort)
	{
		Server.getInstance().getInterfaceHandler().displayToLog("Server waiting on RMI port " + rmiPort + " and Socket port " + socketPort);
		Server.getInstance().getCliListener().shutdownNow();
		Server.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Server.getInstance().setCliStatus(CLIStatus.MAIN);
		Server.getInstance().getCliListener().execute(() -> Server.getCliHandlers().get(Server.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void setupError()
	{
		Server.getInstance().getInterfaceHandler().displayToLog("Server setup failed...");
		Server.getInstance().getCliListener().shutdownNow();
		Server.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Server.getInstance().getCliListener().execute(() -> Server.getCliHandlers().get(Server.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public void displayToLog(String text)
	{
		Server.getLogger().log(Level.INFO, text);
	}

	@Override
	public void handleCommandExecuted()
	{
		Server.getInstance().getCliListener().shutdownNow();
		Server.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Server.getInstance().getCliListener().execute(() -> Server.getCliHandlers().get(Server.getInstance().getCliStatus()).newInstance().execute());
	}
}
