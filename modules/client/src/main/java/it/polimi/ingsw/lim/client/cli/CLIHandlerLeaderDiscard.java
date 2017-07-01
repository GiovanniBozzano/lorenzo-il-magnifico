package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderDiscard;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerLeaderDiscard implements ICLIHandler
{
	private final Map<Integer, Integer> leaderCards = new HashMap<>();
	int leaderCardIndex;

	@Override
	public void execute()
	{
		this.showHandLeaderCards();
		this.askDiscardLeaderCardIndex();
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderDiscard(this.leaderCardIndex));
	}

	@Override
	public CLIHandlerLeaderCardsChoice newInstance()
	{
		return new CLIHandlerLeaderCardsChoice();
	}

	private void showHandLeaderCards()
	{
		int index = 0;
		Client.getLogger().log(Level.INFO, "Enter Discard Leader Card choice...");
		for (int leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
			index++;
			this.leaderCards.put(index, leaderCard);
			Client.getLogger().log(Level.INFO, "{0}===", new Object[] { index });
			Client.getLogger().log(Level.INFO, "{0}\n", new Object[] { GameStatus.getInstance().getLeaderCards().get(leaderCard).getDisplayName() });
		}
	}

	private void askDiscardLeaderCardIndex()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.leaderCards.containsKey(Integer.parseInt(input)));
		this.leaderCardIndex = this.leaderCards.get(Integer.parseInt(input));
	}
}
