package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardBuildingInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardCharacterInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardTerritoryInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardVentureInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.game.utils.ResourceTradeOption;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerShowDevelopmentCards implements ICLIHandler
{
	CardType cardType;
	private final static Map<Integer, CardType> CARD_TYPE_CHOICE = new HashMap<>();

	static {
		CARD_TYPE_CHOICE.put(1, CardType.BUILDING);
		CARD_TYPE_CHOICE.put(2, CardType.CHARACTER);
		CARD_TYPE_CHOICE.put(3, CardType.TERRITORY);
		CARD_TYPE_CHOICE.put(4, CardType.VENTURE);
	}

	@Override
	public void execute()
	{
		this.askDevelopmentCardsType();
		this.showDevelopmentCards();
	}

	private void askDevelopmentCardsType()
	{
		Client.getLogger().log(Level.INFO, "Enter Card Type...");
		Client.getLogger().log(Level.INFO, "1 - BUILDING");
		Client.getLogger().log(Level.INFO, "2 - CHARACTER");
		Client.getLogger().log(Level.INFO, "3 - TERRITORY");
		Client.getLogger().log(Level.INFO, "4 - VENTURE");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.CARD_TYPE_CHOICE.containsKey(Integer.parseInt(input)));
		this.cardType = CLIHandlerShowDevelopmentCards.CARD_TYPE_CHOICE.get(Integer.parseInt(input));
	}

	private void showDevelopmentCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (this.cardType == CardType.BUILDING) {
			for (int index = 0; index < GameStatus.getInstance().getCurrentDevelopmentCardsBuilding().size(); index++) {
				DevelopmentCardBuildingInformations developmentCardBuildingInformations = GameStatus.getInstance().getDevelopmentCardsBuilding(GameStatus.getInstance().getCurrentDevelopmentCardsBuilding());
				boolean firstLine = true;
				stringBuilder.append("============= ");
				stringBuilder.append(index);
				stringBuilder.append(" =============\n");
				if (!developmentCardBuildingInformations.getResourceCostOptions().isEmpty()) {
					firstLine = false;
					stringBuilder.append("RESOURCE COST OPTIONS:\n==============");
					for (ResourceCostOption resourceCostOption : developmentCardBuildingInformations.getResourceCostOptions()) {
						if (!resourceCostOption.getRequiredResources().isEmpty()) {
							stringBuilder.append("\nRequired resources:");
							for (ResourceAmount resourceAmount : resourceCostOption.getRequiredResources()) {
								stringBuilder.append("\n    - ");
								stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(resourceAmount.getAmount());
							}
						}
						if (!resourceCostOption.getSpentResources().isEmpty()) {
							stringBuilder.append("\nSpent resources:");
							for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
								stringBuilder.append("\n    - ");
								stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(resourceAmount.getAmount());
							}
						}
						stringBuilder.append("\n==============");
					}
				}
				if (developmentCardBuildingInformations.getReward().getActionRewardInformations() != null || !developmentCardBuildingInformations.getReward().getResourceAmounts().isEmpty()) {
					if (!firstLine) {
						stringBuilder.append("\n\n");
					}
					stringBuilder.append("REWARD:");
				}
				if (!developmentCardBuildingInformations.getReward().getResourceAmounts().isEmpty()) {
					stringBuilder.append("\nInstant resources:");
					for (ResourceAmount resourceAmount : (developmentCardBuildingInformations.getReward().getResourceAmounts())) {
						stringBuilder.append("\n    - ");
						stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
						stringBuilder.append(": ");
						stringBuilder.append(resourceAmount.getAmount());
					}
				}
				if (developmentCardBuildingInformations.getReward().getActionRewardInformations() != null) {
					stringBuilder.append("\nAction reward:\n| ");
					stringBuilder.append(developmentCardBuildingInformations.getReward().getActionRewardInformations().replace("\n", "\n| "));
				}
				stringBuilder.append("\n\nPRODUCTION ACTIVATION COST: ");
				stringBuilder.append(developmentCardBuildingInformations.getActivationValue());
				if (!developmentCardBuildingInformations.getResourceTradeOptions().isEmpty()) {
					stringBuilder.append("\n\nRESOURCE TRADE OPTIONS:");
					for (ResourceTradeOption resourcetradeOption : developmentCardBuildingInformations.getResourceTradeOptions()) {
						stringBuilder.append("\n==============");
						if (!resourcetradeOption.getEmployedResources().isEmpty()) {
							stringBuilder.append("\nEmployed resources:");
							for (ResourceAmount resourceAmount : resourcetradeOption.getEmployedResources()) {
								stringBuilder.append("\n    - ");
								stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(resourceAmount.getAmount());
							}
						}
						if (!resourcetradeOption.getProducedResources().isEmpty()) {
							stringBuilder.append("\nProduced resources:");
							for (ResourceAmount resourceAmount : resourcetradeOption.getProducedResources()) {
								stringBuilder.append("\n    - ");
								stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(resourceAmount.getAmount());
							}
						}
						stringBuilder.append("\n==============");
					}
				}
				Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
			}
		} else if (this.cardType == CardType.CHARACTER) {
			for (int index = 0; index < GameStatus.getInstance().getCurrentDevelopmentCardsCharacter().size(); index++) {
				DevelopmentCardCharacterInformations developmentCardCharacterInformations = GameStatus.getInstance().getDevelopmentCardsCharacter(GameStatus.getInstance().getCurrentDevelopmentCardsCharacter(index));
				boolean firstLine = true;
				stringBuilder.append("============= ");
				stringBuilder.append(index);
				stringBuilder.append(" =============\n");
				if (!developmentCardCharacterInformations.getResourceCostOptions().isEmpty()) {
					firstLine = false;
					stringBuilder.append("RESOURCE COST OPTIONS:\n==============");
					for (ResourceCostOption resourceCostOption : developmentCardCharacterInformations.getResourceCostOptions()) {
						if (!resourceCostOption.getRequiredResources().isEmpty()) {
							stringBuilder.append("\nRequired resources:");
							for (ResourceAmount resourceAmount : resourceCostOption.getRequiredResources()) {
								stringBuilder.append("\n    - ");
								stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(resourceAmount.getAmount());
							}
						}
						if (!resourceCostOption.getSpentResources().isEmpty()) {
							stringBuilder.append("\nSpent resources:");
							for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
								stringBuilder.append("\n    - ");
								stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(resourceAmount.getAmount());
							}
						}
						stringBuilder.append("\n==============");
					}
				}
				if (developmentCardCharacterInformations.getReward().getActionRewardInformations() != null || !developmentCardCharacterInformations.getReward().getResourceAmounts().isEmpty()) {
					if (!firstLine) {
						stringBuilder.append("\n\n");
					}
					stringBuilder.append("REWARD:");
				}
				if (!developmentCardCharacterInformations.getReward().getResourceAmounts().isEmpty()) {
					stringBuilder.append("\nInstant resources:");
					for (ResourceAmount resourceAmount : (developmentCardCharacterInformations.getReward().getResourceAmounts())) {
						stringBuilder.append("\n    - ");
						stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
						stringBuilder.append(": ");
						stringBuilder.append(resourceAmount.getAmount());
					}
				}
				if (developmentCardCharacterInformations.getReward().getActionRewardInformations() != null) {
					stringBuilder.append("\nAction reward:\n| ");
					stringBuilder.append(developmentCardCharacterInformations.getReward().getActionRewardInformations().replace("\n", "\n| "));
				}
				if (developmentCardCharacterInformations.getModifierInformations() != null) {
					stringBuilder.append("\n\nMODIFIER:\n| ");
					stringBuilder.append(developmentCardCharacterInformations.getModifierInformations().replace("\n", "\n| "));
				}
				Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
			}
		} else if (this.cardType == CardType.TERRITORY) {
			for (int index = 0; index < GameStatus.getInstance().getCurrentDevelopmentCardsTerritory().size(); index++) {
				DevelopmentCardTerritoryInformations developmentCardTerritoryInformations = GameStatus.getInstance().getDevelopmentCardsTerritory(GameStatus.getInstance().getCurrentDevelopmentCardsTerritory(3));
				boolean firstLine = true;
				stringBuilder.append("============= ");
				stringBuilder.append(index);
				stringBuilder.append(" =============\n");
				if (!developmentCardTerritoryInformations.getResourceCostOptions().isEmpty()) {
					firstLine = false;
					stringBuilder.append("RESOURCE COST OPTIONS:\n==============");
					for (ResourceCostOption resourceCostOption : developmentCardTerritoryInformations.getResourceCostOptions()) {
						if (!resourceCostOption.getRequiredResources().isEmpty()) {
							stringBuilder.append("\nRequired resources:");
							for (ResourceAmount resourceAmount : resourceCostOption.getRequiredResources()) {
								stringBuilder.append("\n    - ");
								stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(resourceAmount.getAmount());
							}
						}
						if (!resourceCostOption.getSpentResources().isEmpty()) {
							stringBuilder.append("\nSpent resources:");
							for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
								stringBuilder.append("\n    - ");
								stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(resourceAmount.getAmount());
							}
						}
						stringBuilder.append("\n==============");
					}
				}
				if (developmentCardTerritoryInformations.getReward().getActionRewardInformations() != null || !developmentCardTerritoryInformations.getReward().getResourceAmounts().isEmpty()) {
					if (!firstLine) {
						stringBuilder.append("\n\n");
					}
					stringBuilder.append("REWARD:");
				}
				if (!developmentCardTerritoryInformations.getReward().getResourceAmounts().isEmpty()) {
					stringBuilder.append("\nInstant resources:");
					for (ResourceAmount resourceAmount : (developmentCardTerritoryInformations.getReward().getResourceAmounts())) {
						stringBuilder.append("\n    - ");
						stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
						stringBuilder.append(": ");
						stringBuilder.append(resourceAmount.getAmount());
					}
				}
				if (developmentCardTerritoryInformations.getReward().getActionRewardInformations() != null) {
					stringBuilder.append("\nAction reward:\n| ");
					stringBuilder.append(developmentCardTerritoryInformations.getReward().getActionRewardInformations().replace("\n", "\n| "));
				}
				stringBuilder.append("\n\nHARVEST ACTIVATION VALUE: ");
				stringBuilder.append(developmentCardTerritoryInformations.getActivationValue());
				if (!developmentCardTerritoryInformations.getHarvestResources().isEmpty()) {
					stringBuilder.append("\n\nHARVEST RESOURCES:");
					for (ResourceAmount resourceAmount : developmentCardTerritoryInformations.getHarvestResources()) {
						stringBuilder.append("\n    - ");
						stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
						stringBuilder.append(": ");
						stringBuilder.append(resourceAmount.getAmount());
					}
				}
				Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
			}
		} else if (this.cardType == CardType.VENTURE) {
			for (int index = 0; index < GameStatus.getInstance().getCurrentDevelopmentCardsVenture().size(); index++) {
				DevelopmentCardVentureInformations developmentCardVentureInformations = GameStatus.getInstance().getDevelopmentCardsVenture(GameStatus.getInstance().getCurrentDevelopmentCardsVenture(index));
				boolean firstLine = true;
				stringBuilder.append("============= ");
				stringBuilder.append(index);
				stringBuilder.append(" =============\n");
				if (!developmentCardVentureInformations.getResourceCostOptions().isEmpty()) {
					firstLine = false;
					stringBuilder.append("RESOURCE COST OPTIONS:\n==============");
					for (ResourceCostOption resourceCostOption : developmentCardVentureInformations.getResourceCostOptions()) {
						if (!resourceCostOption.getRequiredResources().isEmpty()) {
							stringBuilder.append("\nRequired resources:");
							for (ResourceAmount resourceAmount : resourceCostOption.getRequiredResources()) {
								stringBuilder.append("\n    - ");
								stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(resourceAmount.getAmount());
							}
						}
						if (!resourceCostOption.getSpentResources().isEmpty()) {
							stringBuilder.append("\nSpent resources:");
							for (ResourceAmount resourceAmount : resourceCostOption.getSpentResources()) {
								stringBuilder.append("\n    - ");
								stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
								stringBuilder.append(": ");
								stringBuilder.append(resourceAmount.getAmount());
							}
						}
						stringBuilder.append("\n==============");
					}
				}
				if (developmentCardVentureInformations.getReward().getActionRewardInformations() != null || !developmentCardVentureInformations.getReward().getResourceAmounts().isEmpty()) {
					if (!firstLine) {
						stringBuilder.append("\n\n");
					}
					stringBuilder.append("REWARD:");
				}
				if (!developmentCardVentureInformations.getReward().getResourceAmounts().isEmpty()) {
					stringBuilder.append("\nInstant resources:");
					for (ResourceAmount resourceAmount : (developmentCardVentureInformations.getReward().getResourceAmounts())) {
						stringBuilder.append("\n    - ");
						stringBuilder.append(Utils.getResourcesTypesNames().get(resourceAmount.getResourceType()));
						stringBuilder.append(": ");
						stringBuilder.append(resourceAmount.getAmount());
					}
				}
				if (developmentCardVentureInformations.getReward().getActionRewardInformations() != null) {
					stringBuilder.append("\nAction reward:\n| ");
					stringBuilder.append(developmentCardVentureInformations.getReward().getActionRewardInformations().replace("\n", "\n| "));
				}
				stringBuilder.append("\nVICTORY VALUE: \n\nVICTORY VALUE: " + developmentCardVentureInformations.getVictoryValue());
			}
		}
	}
}
