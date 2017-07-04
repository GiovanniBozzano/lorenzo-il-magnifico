package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardHarvest;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.logging.Level;

public class CLIHandlerChooseRewardHarvest implements ICLIHandler
{
	@Override
	public void execute()
	{
		this.askServants();
	}

	@Override
	public CLIHandlerChooseRewardHarvest newInstance()
	{
		return new CLIHandlerChooseRewardHarvest();
	}

	private void askServants()
	{
		Client.getLogger().log(Level.INFO, "\n\n\nEnter Servants amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsChooseRewardHarvest(Integer.parseInt(input)));
	}
}
