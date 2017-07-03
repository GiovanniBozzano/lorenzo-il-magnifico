package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
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
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Leader Card choice...");
		int index = 1;
		for (Integer leaderCard : GameStatus.getInstance().getAvailableLeaderCards()) {
			stringBuilder.append('\n');
			stringBuilder.append("============= ");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard));
			stringBuilder.append(" =============\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard).getInformations());
			stringBuilder.append("\n=============================");
			this.leaderCards.put(index, leaderCard);
			index++;
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
