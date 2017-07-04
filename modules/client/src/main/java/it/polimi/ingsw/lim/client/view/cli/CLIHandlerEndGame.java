package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerEndGame implements ICLIHandler
{
	@Override
	public void execute()
	{
		this.showPlayersScores();
		this.askDisconnection();
	}

	@Override
	public CLIHandlerEndGame newInstance()
	{
		return new CLIHandlerEndGame();
	}

	private void showPlayersScores()
	{
		StringBuilder stringBuilder = new StringBuilder();
		int winningPlayerIndex = new ArrayList<>(GameStatus.getInstance().getPlayersScores().keySet()).get(0);
		int winningScore = new ArrayList<>(GameStatus.getInstance().getPlayersScores().values()).get(0);
		for (Entry<Integer, Integer> playerScore : GameStatus.getInstance().getPlayersScores().entrySet()) {
			if (playerScore.getValue() > winningScore) {
				winningPlayerIndex = playerScore.getKey();
				winningScore = playerScore.getValue();
			}
		}
		stringBuilder.append("\n\n\n");
		if (winningPlayerIndex == GameStatus.getInstance().getOwnPlayerIndex()) {
			stringBuilder.append("VICTORY");
		} else {
			stringBuilder.append("DEFEAT");
		}
		int index = 1;
		stringBuilder.append('\n');
		for (Entry<Integer, Integer> playerScore : GameStatus.getInstance().getPlayersScores().entrySet()) {
			stringBuilder.append('\n');
			stringBuilder.append(index);
			stringBuilder.append(" - ");
			stringBuilder.append(GameStatus.getInstance().getCurrentPlayersData().get(playerScore.getKey()).getUsername());
			stringBuilder.append(" - ");
			stringBuilder.append(playerScore.getValue());
			stringBuilder.append(" Points (Record: ");
			stringBuilder.append(GameStatus.getInstance().getPlayerIndexesVictoryPointsRecord().get(playerScore.getKey()));
			stringBuilder.append(" Points)");
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askDisconnection()
	{
		Client.getLogger().log(Level.INFO, "\n\nPress ENTER to disconnect...");
		Client.getInstance().getCliScanner().nextLine();
		Client.getInstance().disconnect(false, true);
	}
}
