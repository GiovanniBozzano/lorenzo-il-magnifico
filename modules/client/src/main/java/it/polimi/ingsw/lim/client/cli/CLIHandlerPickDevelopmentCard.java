package it.polimi.ingsw.lim.client.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionFamilyMember;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformations;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

public class CLIHandlerPickDevelopmentCard implements ICLIHandler
{
	private final Map<Integer, FamilyMemberType> familyMemberTypes = new HashMap<>();
	private static final Map<Integer, CardType> CARD_TYPE_CHOICE = new HashMap<>();
	private final Map<Integer, DevelopmentCardInformations> availableCards = new HashMap<>();
	private final Map<Integer, ResourceCostOption> availableCostOptions = new HashMap<>();
	private int familyMemberValue;
	private DevelopmentCardInformations cardChoiceInformation;
	private ResourceCostOption costOption;

	static {
		CARD_TYPE_CHOICE.put(1, CardType.BUILDING);
		CARD_TYPE_CHOICE.put(2, CardType.CHARACTER);
		CARD_TYPE_CHOICE.put(3, CardType.TERRITORY);
		CARD_TYPE_CHOICE.put(4, CardType.VENTURE);
	}

	@Override
	public void execute()
	{
		this.showFamilyMembers();
		this.askFamilyMember();
		this.askServants();
		this.askDevelopmentCardsType();
		this.askDevelopmentCard();
		this.showCostOptions();
		this.askCostOption();
	}

	@Override
	public CLIHandlerPickDevelopmentCard newInstance()
	{
		return new CLIHandlerPickDevelopmentCard();
	}

	private void showFamilyMembers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		int index = 1;
		boolean firstLine = true;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
			if (!this.familyMemberTypes.containsValue(((AvailableActionFamilyMember) availableAction).getFamilyMemberType())) {
				if (!firstLine) {
					stringBuilder.append('\n');
				} else {
					firstLine = false;
				}
				this.familyMemberTypes.put(index, ((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
				stringBuilder.append(index);
				stringBuilder.append(" ========\n");
				stringBuilder.append(((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askFamilyMember()
	{
		Client.getLogger().log(Level.INFO, "Enter Family Member Choice...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.familyMemberTypes.containsKey(Integer.parseInt(input)));
		this.familyMemberValue = Integer.parseInt(input);
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
		while (!CommonUtils.isInteger(input) || !CLIHandlerPickDevelopmentCard.CARD_TYPE_CHOICE.containsKey(Integer.parseInt(input)));
		this.showDevelopmentCards(CLIHandlerPickDevelopmentCard.CARD_TYPE_CHOICE.get(Integer.parseInt(input)));
	}

	private void showDevelopmentCards(CardType cardType)
	{
		int index = 0;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
			if ((((AvailableActionFamilyMember) availableAction).getFamilyMemberType()) == this.familyMemberTypes.get(familyMemberValue) && (((AvailableActionPickDevelopmentCard) availableAction).getCardType()) == cardType) {
				index++;
				this.availableCards.put(index, GameStatus.getInstance().getDevelopmentCards().get(cardType).get(GameStatus.getInstance().getCurrentDevelopmentCards().get(cardType).get((((AvailableActionPickDevelopmentCard) availableAction).getRow()))));
			}
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (Entry<Integer, DevelopmentCardInformations> currentCard : this.availableCards.entrySet()) {
			stringBuilder.append(currentCard.getKey());
			stringBuilder.append("============\n");
			stringBuilder.append(currentCard.getValue().getInformations());
			Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
		}
	}

	private void askDevelopmentCard()
	{
		Client.getLogger().log(Level.INFO, "Enter Development Card Choice...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableCards.containsKey(Integer.parseInt(input)));
		this.cardChoiceInformation = this.availableCards.get(Integer.parseInt(input));
	}

	private void showCostOptions()
	{
		Client.getLogger().log(Level.INFO, "These are your cost option choices...");
		StringBuilder stringBuilder = new StringBuilder();
		int index = 0;
		if (!this.cardChoiceInformation.getResourceCostOptions().isEmpty()) {
			stringBuilder.append("RESOURCE COST OPTIONS:\n==============");
			for (ResourceCostOption resourceCostOption : this.cardChoiceInformation.getResourceCostOptions()) {
				index++;
				if (!resourceCostOption.getRequiredResources().isEmpty()) {
					stringBuilder.append("\nRequired resources:");
					stringBuilder.append(ResourceAmount.getResourcesInformations(resourceCostOption.getRequiredResources(), true));
				}
				if (!resourceCostOption.getSpentResources().isEmpty()) {
					stringBuilder.append("\nSpent resources:");
					stringBuilder.append(ResourceAmount.getResourcesInformations(resourceCostOption.getSpentResources(), true));
				}
				stringBuilder.append("\n==============");
				this.availableCostOptions.put(index, resourceCostOption);
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askCostOption()
	{
		Client.getLogger().log(Level.INFO, "Enter Cost Option Choice...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableCostOptions.containsKey(Integer.parseInt(input)));
		this.costOption = this.availableCostOptions.get(Integer.parseInt(input));
	}


	private void askServants()
	{
		Client.getLogger().log(Level.INFO, "Enter Servant Amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
	}
}
