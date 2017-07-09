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

	/**
	 * <p>Uses the {@link Integer} {@link PlayerData} {@link Map} of the current
	 * current players data to insert in a {@link
	 * Integer} {@link Integer} {@link Map} the available other
	 * players to perform a show other players action and prints them
	 * and the corresponding choosing indexes on screen.
	 */
	private void showOtherPlayers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Other Player...");
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

	/**
	 * <p>Uses the {@link Integer} {@link Integer} {@link Map} to ask which
	 * {@code chosenOtherPlayer} the current player wants to choose to perform
	 * the action and saves it.
	 */
	private void askOtherPlayer()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableOtherPlayers.containsKey(Integer.parseInt(input)));
		this.chosenOtherPlayer = this.availableOtherPlayers.get(Integer.parseInt(input));
	}

	/**
	 * <p>Asks which leader cards the player wants to see and sends the
	 * corresponding action with the chosen information.
	 */
	private void askLeaderCardPosition()
	{
		Client.getLogger().log(Level.INFO, "\n\nEnter Leader Cards ...");
		Client.getLogger().log(Level.INFO, "Leader Cards in hand: " + GameStatus.getInstance().getCurrentPlayersData().get(this.chosenOtherPlayer).getLeaderCardsInHandNumber());
		Client.getLogger().log(Level.INFO, "1 - Played Leader Cards");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		} while (!CommonUtils.isInteger(input) || Integer.parseInt(input) != 1);
		this.showLeaderCardsPlayed();
	}

	/**
	 * <p>Uses the {@link Integer} {@link Boolean} {@link Map} of the played
	 * leader cards of the {@code chosenOtherPlayer} to print the corresponding
	 * leader cards and if they can be activated on screen.
	 */
	private void showLeaderCardsPlayed()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\nPlayed Leader Cards:");
		for (Entry<Integer, Boolean> leaderCard : GameStatus.getInstance().getCurrentPlayersData().get(this.chosenOtherPlayer).getLeaderCardsPlayed().entrySet()) {
			stringBuilder.append("\n\n========\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(leaderCard.getKey()).getInformation());
			if (!leaderCard.getValue()) {
				Client.getLogger().log(Level.INFO, "\n\nTHIS CARD CANNOT BE ACTIVATED\n");
				Client.getLogger().log(Level.INFO, "====================================\n");
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}
}
