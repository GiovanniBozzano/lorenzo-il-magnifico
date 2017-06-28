package it.polimi.ingsw.lim.server.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.utils.Utils;

public class CLIHandlerMain implements ICLIHandler
{
	@Override
	public void execute()
	{
		this.askCommand();
	}

	private void askCommand()
	{
		Utils.executeCommand(Server.getInstance().getCliScanner().nextLine());
	}

	public static CLIHandlerMain initialize()
	{
		return new CLIHandlerMain();
	}
}
