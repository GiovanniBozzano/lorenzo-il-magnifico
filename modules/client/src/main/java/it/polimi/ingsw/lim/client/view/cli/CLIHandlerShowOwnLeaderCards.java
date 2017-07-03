package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class CLIHandlerShowOwnLeaderCards implements ICLIHandler
{
	private static final Map<Integer, Runnable> LEADER_CARDS_CHOICE = new HashMap<>();

	static {
		LEADER_CARDS_CHOICE.put(1, CLIHandlerShowOwnLeaderCards::showLeaderCardsHand);
		LEADER_CARDS_CHOICE.put(2, CLIHandlerShowOwnLeaderCards::showLeaderCardsPlayed);
	}

	@Override
	public void execute()
	{
		this.askLeaderCardChoice();
		Client.getInstance().getCliListener().shutdownNow();
		Client.getInstance().setCliListener(Executors.newSingleThreadExecutor());
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getCliListener().execute(() -> Client.getCliHandlers().get(Client.getInstance().getCliStatus()).newInstance().execute());
	}

	@Override
	public CLIHandlerShowOwnLeaderCards newInstance()
	{
		return new CLIHandlerShowOwnLeaderCards();
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
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnLeaderCards.LEADER_CARDS_CHOICE.containsKey(Integer.parseInt(input)));
		CLIHandlerShowOwnLeaderCards.LEADER_CARDS_CHOICE.get(Integer.parseInt(input)).run();
	}

	private static void showLeaderCardsHand()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Player Leader Cards in hand:");
		for (Entry<Integer, Boolean> leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().entrySet()) {
			stringBuilder.append("\n========\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getInformations());
			if (!leaderCard.getValue()) {
				Client.getLogger().log(Level.INFO, "\nThis card cannot be played\n");
				Client.getLogger().log(Level.INFO, "=================================\n");
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private static void showLeaderCardsPlayed()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Player Leader Cards played:");
		for (Entry<Integer, Boolean> leaderCard : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed().entrySet()) {
			stringBuilder.append("\n========\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getInformations());
			if (!leaderCard.getValue()) {
				Client.getLogger().log(Level.INFO, "\nThis card cannot be activated\n");
				Client.getLogger().log(Level.INFO, "====================================\n");
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}
}
