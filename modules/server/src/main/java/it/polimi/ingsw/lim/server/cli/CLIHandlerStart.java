package it.polimi.ingsw.lim.server.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;

import java.util.logging.Level;

public class CLIHandlerStart implements ICLIHandler
{
	private int rmiPort;
	private int socketPort;

	@Override
	public void execute()
	{
		this.askRmiPort();
		this.askSocketPort();
		Server.getInstance().setup(this.rmiPort, this.socketPort);
	}

	private void askRmiPort()
	{
		Server.getLogger().log(Level.INFO, "Enter RMI Port [default 8080]...");
		String input;
		do {
			input = Server.getInstance().getCliScanner().nextLine();
		} while (!CommonUtils.isInteger(input));
		this.rmiPort = Integer.parseInt(input);
	}

	private void askSocketPort()
	{
		Server.getLogger().log(Level.INFO, "Enter Socket Port [default 8081]...");
		String input;
		do {
			input = Server.getInstance().getCliScanner().nextLine();
		} while (!CommonUtils.isInteger(input));
		this.socketPort = Integer.parseInt(input);
	}
}
