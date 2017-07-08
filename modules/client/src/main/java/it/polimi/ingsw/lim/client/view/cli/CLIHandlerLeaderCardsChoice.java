package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerLeaderCardsChoice implements ICLIHandler
{
	private final Map<Integer, Integer> availableLeaderCards = new HashMap<>();

	@Override
	public void execute()
	{
		this.showLeaderCards();
		this.askLeaderCard();
	}

	@Override
	public CLIHandlerLeaderCardsChoice newInstance()
	{
		return new CLIHandlerLeaderCardsChoice();
	}

	/**
	 * <p>Uses current leaders that can be chosen from the player to insert in
	 * a {@link Integer} {@link Integer} {@link Map} their indexes and prints
	 * the available leaders and the corresponding choosing indexes
	 * on screen.
	 */
	private void showLeaderCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Leader Card...");
		int index = 1;
		for (int leaderCard : GameStatus.getInstance().getAvailableLeaderCards()) {
			stringBuilder.append(Utils.createListElement(index, GameStatus.getInstance().getLeaderCards().get(leaderCard).getInformation()));
			this.availableLeaderCards.put(index, leaderCard);
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Asks which Leader index the player wants to choose and
	 * sends it.
	 */
	private void askLeaderCard()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableLeaderCards.containsKey(Integer.parseInt(input)));
		Client.getInstance().getConnectionHandler().sendGameLeaderCardPlayerChoice(this.availableLeaderCards.get(Integer.parseInt(input)));
	}
}
