package it.polimi.ingsw.lim.server.view.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.utils.Utils;

public class CLIHandlerMain implements ICLIHandler
{
	private static void askCommand()
	{
		Utils.executeCommand(Server.getInstance().getCliScanner().nextLine());
	}

	@Override
	public void execute()
	{
		CLIHandlerMain.askCommand();
	}

	@Override
	public CLIHandlerMain newInstance()
	{
		return new CLIHandlerMain();
	}
}
