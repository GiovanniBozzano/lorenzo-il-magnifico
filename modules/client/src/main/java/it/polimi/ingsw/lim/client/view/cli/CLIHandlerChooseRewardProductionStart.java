package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationChooseRewardProductionStart;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.logging.Level;

public class CLIHandlerChooseRewardProductionStart implements ICLIHandler
{
	/**
	 * <p>Asks how many servants the player wants to use to increase the action
	 * value and sends the new {@link ActionInformationChooseRewardProductionStart}
	 * with the chosen values.
	 */
	private static void askServants()
	{
		Client.getLogger().log(Level.INFO, "\n\n\nEnter Servants amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationChooseRewardProductionStart(Integer.parseInt(input)));
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
