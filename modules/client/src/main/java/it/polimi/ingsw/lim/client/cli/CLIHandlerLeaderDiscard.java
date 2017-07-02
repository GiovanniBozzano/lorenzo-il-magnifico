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
	private int leaderCardIndex;

	@Override
	public void execute()
	{
		this.showHandLeaderCards();
		this.askDiscardLeaderCardIndex();
	}

	@Override
	public CLIHandlerLeaderCardsChoice newInstance()
	{
		return new CLIHandlerLeaderCardsChoice();
	}

	private void showHandLeaderCards()
	{
		Client.getLogger().log(Level.INFO, "Enter Discard Leader Card choice...");
		StringBuilder stringBuilder = new StringBuilder();
		int index = 1;
		boolean firstLine = true;
		for (int leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
			if (!firstLine) {
				stringBuilder.append('\n');
			} else {
				firstLine = false;
			}
			this.leaderCards.put(index, leaderCard);
			stringBuilder.append(index);
			stringBuilder.append(" ========\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getDisplayName());
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askDiscardLeaderCardIndex()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.leaderCards.containsKey(Integer.parseInt(input)));
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderDiscard(this.leaderCards.get(Integer.parseInt(input))));
	}
}
