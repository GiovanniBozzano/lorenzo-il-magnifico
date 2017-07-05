package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationChooseRewardProductionStart;

public class CLIHandlerChooseRewardProductionStart implements ICLIHandler
{
	/**
	 * <p>Asks how many servants the player wants to use to increase the action
	 * value and sends the new {@link ActionInformationChooseRewardProductionStart}
	 * with the chosen values.
	 */
	private static void askServants()
	{
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationChooseRewardProductionStart(Utils.cliAskServants()));
	}

	@Override
	public void execute()
	{
		CLIHandlerChooseRewardProductionStart.askServants();
	}

	@Override
	public CLIHandlerChooseRewardProductionStart newInstance()
	{
		return new CLIHandlerChooseRewardProductionStart();
	}
}
