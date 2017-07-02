package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;

public class CLIHandlerGameEnded implements ICLIHandler
{
	@Override
	public void execute()
	{
	}

	@Override
	public CLIHandlerExcommunicationChoice newInstance()
	{
		return new CLIHandlerExcommunicationChoice();
	}
}
