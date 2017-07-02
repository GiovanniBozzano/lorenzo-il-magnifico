package it.polimi.ingsw.lim.server.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.server.IInterfaceHandler;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.enums.CLIStatus;
import javafx.stage.Stage;

import java.util.logging.Level;

public class InterfaceHandlerCLI implements IInterfaceHandler
{
	@Override
	public void start()
	{
		Server.getInstance().getCliListener().execute(() -> {
			ICLIHandler cliHandler = Server.getCliHandlers().get(Server.getInstance().getCliStatus()).newInstance();
			Server.getInstance().setCurrentCliHandler(cliHandler);
			cliHandler.execute();
		});
	}

	@Override
	public void start(Stage stage)
	{
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
		Server.getInstance().setCliStatus(CLIStatus.MAIN);
		Server.getInstance().getCliListener().execute(() -> {
			ICLIHandler newCliHandler = Server.getCliHandlers().get(Server.getInstance().getCliStatus()).newInstance();
			Server.getInstance().setCurrentCliHandler(newCliHandler);
			newCliHandler.execute();
		});
	}

	@Override
	public void setupError()
	{
		Server.getInstance().getInterfaceHandler().displayToLog("Server setup failed...");
		Server.getInstance().getCliListener().execute(() -> {
			ICLIHandler newCliHandler = Server.getCliHandlers().get(Server.getInstance().getCliStatus()).newInstance();
			Server.getInstance().setCurrentCliHandler(newCliHandler);
			newCliHandler.execute();
		});
	}

	@Override
	public void displayToLog(String text)
	{
		Server.getLogger().log(Level.INFO, text);
	}

	@Override
	public void handleCommandExecuted()
	{
		Server.getInstance().setCliStatus(CLIStatus.MAIN);
		Server.getInstance().getCliListener().execute(() -> {
			ICLIHandler newCliHandler = Server.getCliHandlers().get(Server.getInstance().getCliStatus()).newInstance();
			Server.getInstance().setCurrentCliHandler(newCliHandler);
			newCliHandler.execute();
		});
	}
}
