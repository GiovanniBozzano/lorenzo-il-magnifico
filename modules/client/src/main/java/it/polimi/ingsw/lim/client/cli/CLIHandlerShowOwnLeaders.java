package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerShowOwnLeaders implements ICLIHandler
{
	private static final Map<Integer, Runnable> LEADER_CARDS_CHOICE = new HashMap<>();

	static {
		LEADER_CARDS_CHOICE.put(1, CLIHandlerShowOwnLeaders::showLeaderCardsHand);
		LEADER_CARDS_CHOICE.put(2, CLIHandlerShowOwnLeaders::showLeaderCardsPlayed);
	}

	@Override
	public void execute()
	{
		this.askLeaderCardChoice();
	}

	@Override
	public CLIHandlerShowOwnLeaders newInstance()
	{
		return new CLIHandlerShowOwnLeaders();
	}

	private void askLeaderCardChoice()
	{
		Client.getLogger().log(Level.INFO, "Enter Leader Cards ...");
		Client.getLogger().log(Level.INFO, "1 - Leader Cards in your hand");
		Client.getLogger().log(Level.INFO, "2 - Your Played Leader Cards");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnLeaders.LEADER_CARDS_CHOICE.containsKey(Integer.parseInt(input)));
		CLIHandlerShowOwnLeaders.LEADER_CARDS_CHOICE.get(Integer.parseInt(input)).run();
	}

	private static void showLeaderCardsHand()
	{
		StringBuilder stringBuilder = new StringBuilder();
		int index = 1;
		boolean firstLine = true;
		for (Entry<Integer, Boolean> leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().entrySet()) {
			if (!firstLine) {
				stringBuilder.append('\n');
			} else {
				firstLine = false;
			}
			stringBuilder.append(index);
			stringBuilder.append(" ========\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getInformations());
			if (!leaderCard.getValue()) {
				Client.getLogger().log(Level.INFO, "\nThe current card cannot be played\n");
				Client.getLogger().log(Level.INFO, "=================================\n");
			}
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private static void showLeaderCardsPlayed()
	{
		StringBuilder stringBuilder = new StringBuilder();
		int index = 1;
		boolean firstLine = true;
		for (Entry<Integer, Boolean> leaderCard : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed().entrySet()) {
			if (!firstLine) {
				stringBuilder.append('\n');
			} else {
				firstLine = false;
			}
			stringBuilder.append(index);
			stringBuilder.append(" ========\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getInformations());
			if (!leaderCard.getValue()) {
				Client.getLogger().log(Level.INFO, "\nThe current card cannot be activated\n");
				Client.getLogger().log(Level.INFO, "====================================\n");
			}
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}
}
