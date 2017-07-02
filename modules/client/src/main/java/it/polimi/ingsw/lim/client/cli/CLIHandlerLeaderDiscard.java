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
	public CLIHandlerLeaderDiscard newInstance()
	{
		return new CLIHandlerLeaderDiscard();
	}

	private void showHandLeaderCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Discard Leader Card choice...");
		int index = 1;
		for (int leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
			stringBuilder.append('\n');
			stringBuilder.append(index);
			stringBuilder.append(" ========\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getDisplayName());
			this.leaderCards.put(index, leaderCard);
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
