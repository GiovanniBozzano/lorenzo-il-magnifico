package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderActivate;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerLeaderActivate implements ICLIHandler
{
	private final Map<Integer, Integer> leaderCards = new HashMap<>();
	private int leaderCardIndex;

	@Override
	public void execute()
	{
		this.showPlayedLeaderCards();
		this.askActivateLeaderCardIndex();
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderActivate(this.leaderCardIndex));
	}

	@Override
	public CLIHandlerLeaderCardsChoice newInstance()
	{
		return new CLIHandlerLeaderCardsChoice();
	}

	private void showPlayedLeaderCards()
	{
		int index = 0;
		Client.getLogger().log(Level.INFO, "Enter Activate Leader Card choice...");
		for (int leaderCard : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed().keySet()) {
			if (GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed().get(leaderCard)) {
				index++;
				this.leaderCards.put(index, leaderCard);
				Client.getLogger().log(Level.INFO, "{0}===", new Object[] { index });
				Client.getLogger().log(Level.INFO, "{0}\n", new Object[] { GameStatus.getInstance().getLeaderCards().get(leaderCard).getDisplayName() });
			}
		}
	}

	private void askActivateLeaderCardIndex()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.leaderCards.containsKey(Integer.parseInt(input)));
		this.leaderCardIndex = this.leaderCards.get(Integer.parseInt(input));
	}
}
