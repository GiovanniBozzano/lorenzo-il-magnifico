package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.cli.IInputHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;

import java.util.HashMap;
import java.util.Map;

public class CLIHandlerActions implements ICLIHandler
{
	private static final Map<Integer, ActionType> ACTION_TYPES = new HashMap<>();

	static {
		CLIHandlerActions.ACTION_TYPES.put(1, ActionType.HARVEST);
		CLIHandlerActions.ACTION_TYPES.put(2, ActionType.PRODUCTION_START);
	}

	private static final Map<Integer, IInputHandler> INPUT_HANDLERS_ACTION_TYPE = new HashMap<>();

	/*static {
		CLIHandlerActions.INPUT_HANDLERS_ACTION_TYPE.put(1, );
		CLIHandlerActions.INPUT_HANDLERS_ACTION_TYPE.put(2, );
	}*/

	@Override
	public void execute()
	{
	}
}
