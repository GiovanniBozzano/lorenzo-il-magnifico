package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.game.player.PlayerData;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerShowOtherLeaderCards implements ICLIHandler
{
	private final Map<Integer, Integer> availableOtherPlayers = new HashMap<>();
	private int chosenOtherPlayer;

	@Override
	public void execute()
	{
		this.showOtherPlayers();
		this.askOtherPlayer();
		this.askLeaderCardPosition();
	}

	@Override
	public CLIHandlerShowOtherLeaderCards newInstance()
	{
		return new CLIHandlerShowOtherLeaderCards();
	}

	private void showOtherPlayers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Other Player...");
		int index = 1;
		for (Entry<Integer, PlayerData> playerData : GameStatus.getInstance().getCurrentPlayersData().entrySet()) {
			if (playerData.getKey() != GameStatus.getInstance().getOwnPlayerIndex()) {
				stringBuilder.append(Utils.createListElement(index, playerData.getValue().getUsername()));
				this.availableOtherPlayers.put(index, playerData.getKey());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askOtherPlayer()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableOtherPlayers.containsKey(Integer.parseInt(input)));
		this.chosenOtherPlayer = this.availableOtherPlayers.get(Integer.parseInt(input));
	}

	private void askLeaderCardPosition()
	{
		Client.getLogger().log(Level.INFO, "Enter Leader Cards ...");
		Client.getLogger().log(Level.INFO, "Leader Cards in hand: " + GameStatus.getInstance().getCurrentPlayersData().get(this.chosenOtherPlayer).getLeaderCardsInHandNumber());
		Client.getLogger().log(Level.INFO, "1 - Played Leader Cards");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		} while (!CommonUtils.isInteger(input) || Integer.parseInt(input) != 1);
		this.showLeaderCardsPlayed();
	}

	private void showLeaderCardsPlayed()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Played Leader Cards:");
		for (Entry<Integer, Boolean> leaderCard : GameStatus.getInstance().getCurrentPlayersData().get(this.chosenOtherPlayer).getLeaderCardsPlayed().entrySet()) {
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
