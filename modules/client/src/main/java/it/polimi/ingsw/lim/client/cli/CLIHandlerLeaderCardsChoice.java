package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardModifierInformations;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardRewardInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
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
		for (int index = 0; index < GameStatus.getInstance().getAvailableLeaderCards().size(); index++) {
			this.leaderCards.put(index + 1, GameStatus.getInstance().getAvailableLeaderCards().get(index));
		}
		this.showLeaderCards();
		this.askLeaderCardsIndex();
	}

	private void showLeaderCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Leader Card choice...");
		for (Entry<Integer, Integer> currentLeaderCard : this.leaderCards.entrySet()) {
			stringBuilder.append('\n');
			stringBuilder.append("============= ");
			stringBuilder.append(currentLeaderCard.getKey());
			stringBuilder.append(" =============\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(currentLeaderCard.getValue()).getDisplayName());
			stringBuilder.append("\n\n");
			stringBuilder.append(GameStatus.getInstance().getLeaderCards().get(currentLeaderCard.getValue()).getDescription());
			stringBuilder.append("\n\n");
			if (GameStatus.getInstance().getLeaderCards().get(currentLeaderCard.getValue()) instanceof LeaderCardModifierInformations) {
				stringBuilder.append("PERMANENT ABILITY:\n");
				stringBuilder.append(((LeaderCardModifierInformations) GameStatus.getInstance().getLeaderCards().get(currentLeaderCard.getValue())).getModifier());
			} else {
				stringBuilder.append("ONCE PER ROUND ABILITY:");
				if (!((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(currentLeaderCard.getValue())).getReward().getResourceAmounts().isEmpty()) {
					stringBuilder.append("\n\nInstant resources:");
				}
				for (ResourceAmount resourceAmount : ((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(currentLeaderCard.getValue())).getReward().getResourceAmounts()) {
					stringBuilder.append('\n');
					stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
					stringBuilder.append(": ");
					stringBuilder.append(resourceAmount.getAmount());
				}
				if (((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(currentLeaderCard.getValue())).getReward().getActionRewardInformations() != null) {
					stringBuilder.append("\n\nAction reward:");
					stringBuilder.append('\n');
					stringBuilder.append(((LeaderCardRewardInformations) GameStatus.getInstance().getLeaderCards().get(currentLeaderCard.getValue())).getReward().getActionRewardInformations());
				}
			}
			stringBuilder.append("=============================");
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

	@Override
	public CLIHandlerLeaderCardsChoice newInstance()
	{
		return new CLIHandlerLeaderCardsChoice();
	}
}
