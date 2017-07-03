package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;

public class CLIHandlerProductionTrade implements ICLIHandler
{
	@Override
	public void execute()
	{
	}

	@Override
	public CLIHandlerProductionTrade newInstance()
	{
		return new CLIHandlerProductionTrade();
	}
}
