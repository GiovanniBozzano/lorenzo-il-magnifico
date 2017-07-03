package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionFamilyMember;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerPickDevelopmentCard implements ICLIHandler
{
	private static final Map<Integer, CardType> CARD_TYPE_CHOICE = new HashMap<>();

	static {
		CARD_TYPE_CHOICE.put(1, CardType.BUILDING);
		CARD_TYPE_CHOICE.put(2, CardType.CHARACTER);
		CARD_TYPE_CHOICE.put(3, CardType.TERRITORY);
		CARD_TYPE_CHOICE.put(4, CardType.VENTURE);
	}

	private final Map<Integer, FamilyMemberType> availableFamilyMemberTypes = new HashMap<>();
	private final Map<Integer, Row> availableRows = new HashMap<>();
	private final Map<Integer, ResourceCostOption> availableResourceCostOptions = new HashMap<>();
	private FamilyMemberType familyMemberType;
	private CardType cardType;
	private Row row;
	private int servants;
	private ResourceCostOption resourceCostOption;

	@Override
	public void execute()
	{
		this.showFamilyMembers();
		this.askFamilyMember();
		this.askDevelopmentCardsType();
		this.showDevelopmentCards();
		this.askDevelopmentCard();
		this.askServants();
		if (!GameStatus.getInstance().getDevelopmentCards().get(this.cardType).get(GameStatus.getInstance().getCurrentDevelopmentCards().get(this.cardType).get(this.row)).getResourceCostOptions().isEmpty()) {
			this.showCostOptions();
			this.askCostOption();
		}
	}

	@Override
	public CLIHandlerPickDevelopmentCard newInstance()
	{
		return new CLIHandlerPickDevelopmentCard();
	}

	private void showFamilyMembers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Family Member Choice...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
			if (!this.availableFamilyMemberTypes.containsValue(((AvailableActionFamilyMember) availableAction).getFamilyMemberType())) {
				stringBuilder.append('\n');
				stringBuilder.append(index);
				stringBuilder.append(" ========\n");
				stringBuilder.append(((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
				this.availableFamilyMemberTypes.put(index, ((AvailableActionFamilyMember) availableAction).getFamilyMemberType());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askFamilyMember()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableFamilyMemberTypes.containsKey(Integer.parseInt(input)));
		this.familyMemberType = this.availableFamilyMemberTypes.get(Integer.parseInt(input));
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
		this.cardType = CLIHandlerPickDevelopmentCard.CARD_TYPE_CHOICE.get(Integer.parseInt(input));
	}

	private void showDevelopmentCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Development Card Choice...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
			if ((((AvailableActionFamilyMember) availableAction).getFamilyMemberType()) == this.familyMemberType && (((AvailableActionPickDevelopmentCard) availableAction).getCardType()) == this.cardType) {
				stringBuilder.append('\n');
				stringBuilder.append(index);
				stringBuilder.append(" ==============\n");
				stringBuilder.append(GameStatus.getInstance().getDevelopmentCards().get(this.cardType).get(GameStatus.getInstance().getCurrentDevelopmentCards().get(this.cardType).get(((AvailableActionPickDevelopmentCard) availableAction).getRow())).getInformations());
				this.availableRows.put(index, ((AvailableActionPickDevelopmentCard) availableAction).getRow());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askDevelopmentCard()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableRows.containsKey(Integer.parseInt(input)));
		this.row = this.availableRows.get(Integer.parseInt(input));
	}

	private void askServants()
	{
		Client.getLogger().log(Level.INFO, "Enter Servant Amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		this.servants = Integer.parseInt(input);
	}

	private void showCostOptions()
	{
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
			if ((((AvailableActionPickDevelopmentCard) availableAction).getFamilyMemberType()) == this.familyMemberType && (((AvailableActionPickDevelopmentCard) availableAction).getCardType()) == this.cardType && (((AvailableActionPickDevelopmentCard) availableAction).getRow()) == this.row) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Enter Cost Option Choice...");
				int index = 1;
				for (ResourceCostOption resourceCostOption : ((AvailableActionPickDevelopmentCard) availableAction).getResourceCostOptions()) {
					stringBuilder.append('\n');
					stringBuilder.append(index);
					stringBuilder.append(" ==============\n");
					if (!resourceCostOption.getRequiredResources().isEmpty()) {
						stringBuilder.append("\nRequired resources:");
						stringBuilder.append(ResourceAmount.getResourcesInformations(resourceCostOption.getRequiredResources(), true));
					}
					if (!resourceCostOption.getSpentResources().isEmpty()) {
						stringBuilder.append("\nSpent resources:");
						stringBuilder.append(ResourceAmount.getResourcesInformations(resourceCostOption.getSpentResources(), true));
					}
					stringBuilder.append("\n==============");
					this.availableResourceCostOptions.put(index, resourceCostOption);
					index++;
				}
				Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
				break;
			}
		}
	}

	private void askCostOption()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableResourceCostOptions.containsKey(Integer.parseInt(input)));
		this.resourceCostOption = this.availableResourceCostOptions.get(Integer.parseInt(input));
	}
}
