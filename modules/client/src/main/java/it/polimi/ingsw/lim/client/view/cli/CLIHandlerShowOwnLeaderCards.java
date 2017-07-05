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
	private static final Map<Integer, Runnable> LEADER_CARDS_POSITIONS = new HashMap<>();

	static {
		CLIHandlerShowOwnLeaderCards.LEADER_CARDS_POSITIONS.put(1, CLIHandlerShowOwnLeaderCards::showLeaderCardsHand);
		CLIHandlerShowOwnLeaderCards.LEADER_CARDS_POSITIONS.put(2, CLIHandlerShowOwnLeaderCards::showLeaderCardsPlayed);
	}

	private static void showLeaderCardsHand()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\nLeader Cards in hand:");
		for (Entry<Integer, Boolean> leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().entrySet()) {
			stringBuilder.append("\n\n========\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getInformation());
			if (!leaderCard.getValue()) {
				stringBuilder.append("\n\nTHIS CARD CANNOT BE PLAYED\n");
				stringBuilder.append("=================================\n");
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private static void showLeaderCardsPlayed()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Played Leader Cards:");
		for (Entry<Integer, Boolean> leaderCard : GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getLeaderCardsPlayed().entrySet()) {
			stringBuilder.append("\n\n========\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getInformation());
			if (!leaderCard.getValue()) {
				stringBuilder.append("\n\nTHIS CARD CANNOT BE ACTIVATED\n");
				stringBuilder.append("====================================\n");
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private static void askLeaderCardPosition()
	{
		Client.getLogger().log(Level.INFO, "\n\n\nEnter Leader Cards ...");
		Client.getLogger().log(Level.INFO, "1 - Leader Cards in your hand");
		Client.getLogger().log(Level.INFO, "2 - Played Leader Cards");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerShowOwnLeaderCards.LEADER_CARDS_POSITIONS.containsKey(Integer.parseInt(input)));
		CLIHandlerShowOwnLeaderCards.LEADER_CARDS_POSITIONS.get(Integer.parseInt(input)).run();
	}

	@Override
	public void execute()
	{
		CLIHandlerShowOwnLeaderCards.askLeaderCardPosition();
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
}
