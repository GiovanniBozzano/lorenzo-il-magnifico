package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerLeaderCardsChoice implements  ICLIHandler
{
		private boolean ownTurn = false;
		private final Map<Integer, Integer> leaderCards = new HashMap<>();

		@Override
		public void execute()
		{
			for (int index = 0; index < GameStatus.getInstance().getAvailableLeaderCards().size(); index++) {
				this.leaderCards.put(index + 1, GameStatus.getInstance().getAvailableLeaderCards().get(index));
			}

			this.askLeaderCardsIndex();

		}

		private void askLeaderCardsIndex()
		{
			String input;
			do {
				input = Client.getInstance().getCliScanner().nextLine();
			}
			while (!CommonUtils.isInteger(input) || !this.leaderCards.containsKey(Integer.parseInt(input)) || !this.ownTurn);
			Client.getInstance().getConnectionHandler().sendGameLeaderCardPlayerChoice(this.leaderCards.get(Integer.parseInt(input)));
		}
		public boolean isOwnTurn()
		{
			return this.ownTurn;
		}

		public void setOwnTurn(boolean ownTurn)
		{
			if (ownTurn) {
				Client.getLogger().log(Level.INFO, "Enter LeaderCard choice...");
				for(Entry<Integer, Integer> leaderCard : this.leaderCards.entrySet()){
					Client.getLogger().log(Level.INFO, "== {0} ============", new Object[] { leaderCard.getKey() });

					Client.getLogger().log(Level.INFO, "===================");

					GameStatus.getInstance().getLeaderCards().get(leaderCard.getValue());
				}
			}
			this.ownTurn = ownTurn;
		}

}
