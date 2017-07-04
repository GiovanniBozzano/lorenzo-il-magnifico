package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionFamilyMember;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerPickDevelopmentCard implements ICLIHandler
{
	private final Map<Integer, FamilyMemberType> availableFamilyMemberTypes = new HashMap<>();
	private final Map<Integer, CardType> availableCardTypes = new HashMap<>();
	private final Map<Integer, AvailableActionPickDevelopmentCard> availableDevelopmentCards = new HashMap<>();
	private final Map<Integer, ResourceCostOption> availableResourceCostOptions = new HashMap<>();
	private final Map<Integer, List<ResourceAmount>> availableDiscountChoices = new HashMap<>();
	private FamilyMemberType chosenFamilyMemberType;
	private CardType chosenCardType;
	private AvailableActionPickDevelopmentCard chosenDevelopmentCard;
	private ResourceCostOption chosenResourceCostOption;
	private List<ResourceAmount> chosenDiscountChoice;

	@Override
	public void execute()
	{
		this.showFamilyMembers();
		this.askFamilyMember();
		this.showDevelopmentCardTypes();
		this.askCardType();
		this.showDevelopmentCards();
		this.askDevelopmentCard();
		if (!this.chosenDevelopmentCard.getResourceCostOptions().isEmpty()) {
			this.showResourceCostOptions();
			this.askResourceCostOption();
			if (!this.chosenDevelopmentCard.getDiscountChoices().isEmpty()) {
				this.showDiscountChoices();
				this.askDiscountChoice();
			}
		}
		this.askServants();
	}

	@Override
	public CLIHandlerPickDevelopmentCard newInstance()
	{
		return new CLIHandlerPickDevelopmentCard();
	}

	private void showFamilyMembers()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Family Member...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
			if (!this.availableFamilyMemberTypes.containsValue(((AvailableActionFamilyMember) availableAction).getFamilyMemberType())) {
				stringBuilder.append(Utils.createListElement(index, ((AvailableActionFamilyMember) availableAction).getFamilyMemberType().name()));
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
		this.chosenFamilyMemberType = this.availableFamilyMemberTypes.get(Integer.parseInt(input));
	}

	private void showDevelopmentCardTypes()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Card Type...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
			if (((AvailableActionFamilyMember) availableAction).getFamilyMemberType() == this.chosenFamilyMemberType && !this.availableCardTypes.containsValue(((AvailableActionPickDevelopmentCard) availableAction).getCardType())) {
				stringBuilder.append(Utils.createListElement(index, ((AvailableActionFamilyMember) availableAction).getFamilyMemberType().name()));
				this.availableCardTypes.put(index, ((AvailableActionPickDevelopmentCard) availableAction).getCardType());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askCardType()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableCardTypes.containsKey(Integer.parseInt(input)));
		this.chosenCardType = this.availableCardTypes.get(Integer.parseInt(input));
	}

	private void showDevelopmentCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Development Card...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
			if ((((AvailableActionFamilyMember) availableAction).getFamilyMemberType()) == this.chosenFamilyMemberType && (((AvailableActionPickDevelopmentCard) availableAction).getCardType()) == this.chosenCardType) {
				stringBuilder.append(Utils.createListElement(index, GameStatus.getInstance().getDevelopmentCards().get(this.chosenCardType).get(GameStatus.getInstance().getCurrentDevelopmentCards().get(this.chosenCardType).get(((AvailableActionPickDevelopmentCard) availableAction).getRow())).getInformations()));
				this.availableDevelopmentCards.put(index, (AvailableActionPickDevelopmentCard) availableAction);
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
		while (!CommonUtils.isInteger(input) || !this.availableDevelopmentCards.containsKey(Integer.parseInt(input)));
		this.chosenDevelopmentCard = this.availableDevelopmentCards.get(Integer.parseInt(input));
	}

	private void showResourceCostOptions()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Resource Cost Option...");
		int index = 1;
		for (ResourceCostOption resourceCostOption : this.chosenDevelopmentCard.getResourceCostOptions()) {
			stringBuilder.append(Utils.createListElement(index, (!resourceCostOption.getRequiredResources().isEmpty() ? "\nRequired resources:\n" + ResourceAmount.getResourcesInformations(resourceCostOption.getRequiredResources(), true) : "") + (!resourceCostOption.getSpentResources().isEmpty() ? "\nSpent resources:\n" + ResourceAmount.getResourcesInformations(resourceCostOption.getSpentResources(), true) : "")));
			this.availableResourceCostOptions.put(index, resourceCostOption);
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askResourceCostOption()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableResourceCostOptions.containsKey(Integer.parseInt(input)));
		this.chosenResourceCostOption = this.availableResourceCostOptions.get(Integer.parseInt(input));
	}

	private void showDiscountChoices()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Discount Choice...");
		int index = 1;
		for (List<ResourceAmount> resourceAmounts : this.chosenDevelopmentCard.getDiscountChoices()) {
			stringBuilder.append(ResourceAmount.getResourcesInformations(resourceAmounts, true));
			this.availableDiscountChoices.put(index, resourceAmounts);
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askDiscountChoice()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableDiscountChoices.containsKey(Integer.parseInt(input)));
		this.chosenDiscountChoice = this.availableDiscountChoices.get(Integer.parseInt(input));
	}

	private void askServants()
	{
		Client.getLogger().log(Level.INFO, "Enter Servants amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsPickDevelopmentCard(this.chosenFamilyMemberType, Integer.parseInt(input), this.chosenCardType, this.chosenDevelopmentCard.getRow(), this.chosenDiscountChoice, this.chosenResourceCostOption));
	}
}
