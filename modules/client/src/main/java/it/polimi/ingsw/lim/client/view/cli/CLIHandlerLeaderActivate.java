package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderActivate;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerLeaderActivate implements ICLIHandler
{
	private final Map<Integer, Integer> leaderCards = new HashMap<>();

	@Override
	public void execute()
	{
		this.showPlayedLeaderCards();
		this.askActivateLeaderCardIndex();
	}

	@Override
	public CLIHandlerLeaderActivate newInstance()
	{
		return new CLIHandlerLeaderActivate();
	}

	private void showPlayedLeaderCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Activate Leader Card choice...");
		int index = 1;
		for (int leaderCard : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed().keySet()) {
			if (GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed().get(leaderCard)) {
				stringBuilder.append(Utils.createListElement(index, GameStatus.getInstance().getLeaderCards().get(leaderCard).getInformations()));
				this.leaderCards.put(index, leaderCard);
				index++;
			}
		}
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askActivateLeaderCardIndex()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.leaderCards.containsKey(Integer.parseInt(input)));
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderActivate(Integer.parseInt(input)));
	}
}
