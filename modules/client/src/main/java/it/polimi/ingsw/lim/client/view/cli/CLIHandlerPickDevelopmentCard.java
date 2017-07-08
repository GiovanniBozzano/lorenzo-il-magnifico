package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.enums.CLIStatus;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionFamilyMember;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
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
	private final List<ResourceAmount> chosenDiscountChoice = new ArrayList<>();
	private FamilyMemberType chosenFamilyMemberType;
	private CardType chosenCardType;
	private AvailableActionPickDevelopmentCard chosenDevelopmentCard;
	private ResourceCostOption chosenResourceCostOption;

	@Override
	public void execute()
	{
		this.showFamilyMemberTypes();
		this.askFamilyMemberType();
		this.showCardTypes();
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

	/**
	 * <p>Uses current available actions of the player to insert in a {@link
	 * Integer} {@link FamilyMemberType} {@link Map} the available family
	 * members to perform an {@link ActionInformationPickDevelopmentCard} and
	 * prints them and the corresponding choosing indexes on screen.
	 */
	private void showFamilyMemberTypes()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Family Member...");
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

	/**
	 * <p>Asks which {@link FamilyMemberType} the player wants to use to perform
	 * the action and saves it.
	 */
	private void askFamilyMemberType()
	{
		this.chosenFamilyMemberType = Utils.cliAskFamilyMemberType(this.availableFamilyMemberTypes);
	}

	/**
	 * <p>Uses current available actions of the player to insert in a {@link
	 * Integer} {@link CardType} {@link Map} the available card types to perform
	 * an {@link ActionInformationPickDevelopmentCard} with the {@code
	 * chosenFamilyMemberType} and prints them and the corresponding choosing
	 * indexes on screen.
	 */
	private void showCardTypes()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Card Type...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
			if (((AvailableActionPickDevelopmentCard) availableAction).getFamilyMemberType() == this.chosenFamilyMemberType && !this.availableCardTypes.containsValue(((AvailableActionPickDevelopmentCard) availableAction).getCardType())) {
				stringBuilder.append(Utils.createListElement(index, ((AvailableActionPickDevelopmentCard) availableAction).getCardType().name()));
				this.availableCardTypes.put(index, ((AvailableActionPickDevelopmentCard) availableAction).getCardType());
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Asks which {@link CardType} the player wants to use to perform
	 * the action and saves it.
	 */
	private void askCardType()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableCardTypes.containsKey(Integer.parseInt(input)));
		this.chosenCardType = this.availableCardTypes.get(Integer.parseInt(input));
	}

	/**
	 * <p>Uses current available actions of the player to insert in a {@link
	 * Integer} {@link AvailableActionPickDevelopmentCard} {@link Map} the
	 * available pick development card actions to perform an {@link
	 * ActionInformationPickDevelopmentCard} with the {@code
	 * chosenFamilyMemberType} and the {@code chosenCardType} and prints them
	 * and the corresponding choosing indexes on screen.
	 */
	private void showDevelopmentCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Development Card...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.PICK_DEVELOPMENT_CARD)) {
			if ((((AvailableActionFamilyMember) availableAction).getFamilyMemberType()) == this.chosenFamilyMemberType && (((AvailableActionPickDevelopmentCard) availableAction).getCardType()) == this.chosenCardType) {
				stringBuilder.append(Utils.createListElement(index, GameStatus.getInstance().getDevelopmentCards().get(this.chosenCardType).get(GameStatus.getInstance().getCurrentDevelopmentCards().get(this.chosenCardType).get(((AvailableActionPickDevelopmentCard) availableAction).getRow())).getInformation()));
				this.availableDevelopmentCards.put(index, (AvailableActionPickDevelopmentCard) availableAction);
				index++;
			}
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Asks which {@link AvailableActionPickDevelopmentCard} the player wants
	 * to use to perform the action and saves it.
	 */
	private void askDevelopmentCard()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableDevelopmentCards.containsKey(Integer.parseInt(input)));
		this.chosenDevelopmentCard = this.availableDevelopmentCards.get(Integer.parseInt(input));
	}

	/**
	 * <p>Uses the {@code chosenDevelopmentCard} to insert in a {@link
	 * Integer} {@link ResourceCostOption} {@link Map} the
	 * available resource cost options to perform the {@link
	 * ActionInformationPickDevelopmentCard} and prints them
	 * and the corresponding choosing indexes on screen.
	 */
	private void showResourceCostOptions()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Resource Cost Option...");
		int index = 1;
		for (ResourceCostOption resourceCostOption : this.chosenDevelopmentCard.getResourceCostOptions()) {
			stringBuilder.append(Utils.createListElement(index, resourceCostOption.getInformation(true)));
			this.availableResourceCostOptions.put(index, resourceCostOption);
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Asks which {@link ResourceCostOption} the player wants
	 * to use to perform the action and saves it.
	 */
	private void askResourceCostOption()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableResourceCostOptions.containsKey(Integer.parseInt(input)));
		this.chosenResourceCostOption = this.availableResourceCostOptions.get(Integer.parseInt(input));
	}

	/**
	 * <p>Uses the {@code chosenDevelopmentCard} to insert in a {@link
	 * Integer} {@link List<ResourceAmount>} {@link Map} the
	 * available discount choices to perform the {@link
	 * ActionInformationPickDevelopmentCard} and prints them
	 * and the corresponding choosing indexes on screen.
	 */
	private void showDiscountChoices()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Discount Choice...");
		int index = 1;
		for (List<ResourceAmount> resourceAmounts : this.chosenDevelopmentCard.getDiscountChoices()) {
			stringBuilder.append(Utils.createListElement(index, ResourceAmount.getResourcesInformation(resourceAmounts, true)));
			this.availableDiscountChoices.put(index, resourceAmounts);
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	/**
	 * <p>Asks which {@link List<ResourceAmount>} the player wants
	 * to use to perform the action and saves it.
	 */
	private void askDiscountChoice()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableDiscountChoices.containsKey(Integer.parseInt(input)));
		this.chosenDiscountChoice.addAll(this.availableDiscountChoices.get(Integer.parseInt(input)));
	}

	/**
	 * <p>Asks how many servants the player wants to use to increase the action
	 * value and sends the new {@link ActionInformationPickDevelopmentCard} with
	 * the chosen values.
	 */
	private void askServants()
	{
		Client.getInstance().setCliStatus(CLIStatus.AVAILABLE_ACTIONS);
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationPickDevelopmentCard(this.chosenFamilyMemberType, Utils.cliAskServants(), this.chosenCardType, this.chosenDevelopmentCard.getRow(), this.chosenDiscountChoice, this.chosenResourceCostOption));
	}
}
