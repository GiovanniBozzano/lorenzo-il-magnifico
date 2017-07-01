package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsLeaderPlay;
import it.polimi.ingsw.lim.common.game.utils.CardAmount;
import it.polimi.ingsw.lim.common.game.utils.LeaderCardConditionsOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerLeaderPlay implements ICLIHandler
{
	private final Map<Integer, Integer> leaderCards = new HashMap<>();
	int leaderCardIndex;

	@Override
	public void execute()
	{
		this.showHandLeaderCards();
		this.askPlayLeaderCardIndex();
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsLeaderPlay(this.leaderCardIndex));
	}

	@Override
	public CLIHandlerLeaderCardsChoice newInstance()
	{
		return new CLIHandlerLeaderCardsChoice();
	}

	private void showHandLeaderCards()
	{
		int index = 0;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Play Leader Card choice...");
		for (int leaderCard : GameStatus.getInstance().getCurrentOwnLeaderCardsHand().keySet()) {
			for (LeaderCardConditionsOption leaderCardConditionsOption : GameStatus.getInstance().getLeaderCards().get(leaderCard).getConditionsOptions()) {
				boolean availableConditionOption = true;
				if (leaderCardConditionsOption.getResourceAmounts() != null) {
					for (ResourceAmount requiredResources : leaderCardConditionsOption.getResourceAmounts()) {
						int playerResources = GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(requiredResources.getResourceType());
						if (playerResources < requiredResources.getAmount()) {
							availableConditionOption = false;
							break;
						}
					}
				}
				int playerCards;
				if (leaderCardConditionsOption.getCardAmounts() != null) {
					for (CardAmount requiredCards : leaderCardConditionsOption.getCardAmounts()) {
						if (requiredCards.getCardType() == CardType.BUILDING) {
							playerCards = GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCardsBuilding().size();
							if (playerCards < requiredCards.getAmount()) {
								availableConditionOption = false;
								break;
							}
						} else if (requiredCards.getCardType() == CardType.CHARACTER) {
							playerCards = GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCardsCharacter().size();
							if (playerCards < requiredCards.getAmount()) {
								availableConditionOption = false;
								break;
							}
						} else if (requiredCards.getCardType() == CardType.TERRITORY) {
							playerCards = GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCardsTerritory().size();
							if (playerCards < requiredCards.getAmount()) {
								availableConditionOption = false;
								break;
							}
						} else {
							playerCards = GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getDevelopmentCardsVenture().size();
							if (playerCards < requiredCards.getAmount()) {
								availableConditionOption = false;
								break;
							}
						}
					}
					if (availableConditionOption) {
						index++;
						this.leaderCards.put(index, leaderCard);
						Client.getLogger().log(Level.INFO, "{0}===", new Object[] { index });
						Client.getLogger().log(Level.INFO, "{0}\n", new Object[] { GameStatus.getInstance().getLeaderCards().get(leaderCard).getDisplayName() });
					}
				}
			}
		}
	}

	private void askPlayLeaderCardIndex()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.leaderCards.containsKey(Integer.parseInt(input)));
		this.leaderCardIndex = this.leaderCards.get(Integer.parseInt(input));
	}
}