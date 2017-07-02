package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerLeaderCardsChoice implements ICLIHandler
{
	private final Map<Integer, Integer> leaderCards = new HashMap<>();

	@Override
	public void execute()
	{
		this.showLeaderCards();
		this.askLeaderCardsIndex();
	}

	@Override
	public CLIHandlerLeaderCardsChoice newInstance()
	{
		return new CLIHandlerLeaderCardsChoice();
	}

	private void showLeaderCards()
	{
		for (int index = 0; index < GameStatus.getInstance().getAvailableLeaderCards().size(); index++) {
			this.leaderCards.put(index + 1, GameStatus.getInstance().getAvailableLeaderCards().get(index));
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Leader Card choice...");
		for (Entry<Integer, Integer> leaderCard : this.leaderCards.entrySet()) {
			stringBuilder.append('\n');
			stringBuilder.append("============= ");
			stringBuilder.append(leaderCard.getKey());
			stringBuilder.append(" =============\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getValue()).getInformations());
			stringBuilder.append("\n=============================");
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askLeaderCardsIndex()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.leaderCards.containsKey(Integer.parseInt(input)));
		Client.getInstance().getConnectionHandler().sendGameLeaderCardPlayerChoice(this.leaderCards.get(Integer.parseInt(input)));
	}
}
