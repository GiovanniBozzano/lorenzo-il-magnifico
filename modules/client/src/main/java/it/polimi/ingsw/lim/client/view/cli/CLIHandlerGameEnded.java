package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.common.cli.ICLIHandler;

public class CLIHandlerGameEnded implements ICLIHandler
{
	@Override
	public void execute()
	{
	}

	@Override
	public CLIHandlerGameEnded newInstance()
	{
		return new CLIHandlerGameEnded();
	}
}
