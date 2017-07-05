package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationChooseRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionChooseRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerChooseRewardPickDevelopmentCard implements ICLIHandler
{
	private static final Map<Integer, Row> INSTANT_REWARD_ROWS = new HashMap<>();

	static {
		CLIHandlerChooseRewardPickDevelopmentCard.INSTANT_REWARD_ROWS.put(1, Row.THIRD);
		CLIHandlerChooseRewardPickDevelopmentCard.INSTANT_REWARD_ROWS.put(2, Row.FOURTH);
	}

	private final Map<Integer, CardType> availableCardTypes = new HashMap<>();
	private final Map<Integer, AvailableActionChooseRewardPickDevelopmentCard> availableDevelopmentCards = new HashMap<>();
	private final Map<Integer, ResourceCostOption> availableResourceCostOptions = new HashMap<>();
	private final Map<Integer, List<ResourceAmount>> availableInstantDiscountChoices = new HashMap<>();
	private final Map<Integer, List<ResourceAmount>> availableDiscountChoices = new HashMap<>();
	private final List<ResourceAmount> chosenInstantDiscountChoice = new ArrayList<>();
	private final List<ResourceAmount> chosenDiscountChoice = new ArrayList<>();
	private CardType chosenCardType;
	private AvailableActionChooseRewardPickDevelopmentCard chosenDevelopmentCard;
	private Row chosenInstantRewardRow;
	private ResourceCostOption chosenResourceCostOption;

	@Override
	public void execute()
	{
		this.showCardTypes();
		this.askCardType();
		this.showDevelopmentCards();
		this.askDevelopmentCard();
		this.askInstantRewardRow();
		if (this.chosenDevelopmentCard.getResourceCostOptions().isEmpty()) {
			this.showResourceCostOptions();
			this.askResourceCostOption();
			if (!this.chosenDevelopmentCard.getDiscountChoices().isEmpty()) {
				this.showInstantDiscountChoices();
				this.askInstantDiscountChoice();
			}
			if (this.chosenDevelopmentCard.getDiscountChoices() != null) {
				this.showDiscountChoices();
				this.askDiscountChoice();
			}
			this.askServants();
		}
	}

	@Override
	public CLIHandlerChooseRewardPickDevelopmentCard newInstance()
	{
		return new CLIHandlerChooseRewardPickDevelopmentCard();
	}

	private void showCardTypes()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\n\nEnter Card Type...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD)) {
			if (!this.availableCardTypes.containsValue(((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType())) {
				stringBuilder.append(Utils.createListElement(index, ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType().name()));
				this.availableCardTypes.put(index, ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType());
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
		stringBuilder.append("\n\nEnter Development Card...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD)) {
			if ((((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType()) == this.chosenCardType) {
				stringBuilder.append(Utils.createListElement(index, GameStatus.getInstance().getDevelopmentCards().get(this.chosenCardType).get(GameStatus.getInstance().getCurrentDevelopmentCards().get(this.chosenCardType).get(((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getRow())).getInformation()));
				this.availableDevelopmentCards.put(index, (AvailableActionChooseRewardPickDevelopmentCard) availableAction);
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

	private void askInstantRewardRow()
	{
		Client.getLogger().log(Level.INFO, "\n\nEnter Instant Reward Row...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !CLIHandlerChooseRewardPickDevelopmentCard.INSTANT_REWARD_ROWS.containsKey(Integer.parseInt(input)));
		this.chosenInstantRewardRow = CLIHandlerChooseRewardPickDevelopmentCard.INSTANT_REWARD_ROWS.get(Integer.parseInt(input));
	}

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

	private void askResourceCostOption()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableResourceCostOptions.containsKey(Integer.parseInt(input)));
		this.chosenResourceCostOption = this.availableResourceCostOptions.get(Integer.parseInt(input));
	}

	private void showInstantDiscountChoices()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Instant Discount Choice...");
		int index = 1;
		for (List<ResourceAmount> resourceAmounts : this.chosenDevelopmentCard.getInstantDiscountChoices()) {
			stringBuilder.append(ResourceAmount.getResourcesInformation(resourceAmounts, true));
			this.availableInstantDiscountChoices.put(index, resourceAmounts);
			index++;
		}
		Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
	}

	private void askInstantDiscountChoice()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableInstantDiscountChoices.containsKey(Integer.parseInt(input)));
		this.chosenInstantDiscountChoice.addAll(this.availableInstantDiscountChoices.get(Integer.parseInt(input)));
	}

	private void showDiscountChoices()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("\n\nEnter Discount Choice...");
		int index = 1;
		for (List<ResourceAmount> resourceAmounts : this.chosenDevelopmentCard.getDiscountChoices()) {
			stringBuilder.append(ResourceAmount.getResourcesInformation(resourceAmounts, true));
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
		this.chosenDiscountChoice.addAll(this.availableDiscountChoices.get(Integer.parseInt(input)));
	}

	private void askServants()
	{
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationChooseRewardPickDevelopmentCard(Utils.cliAskServants(), this.chosenCardType, this.chosenDevelopmentCard.getRow(), this.chosenInstantRewardRow, this.chosenInstantDiscountChoice, this.chosenDiscountChoice, this.chosenResourceCostOption));
	}
}
