package it.polimi.ingsw.lim.client.view.cli;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.game.GameStatus;
import it.polimi.ingsw.lim.client.utils.Utils;
import it.polimi.ingsw.lim.common.cli.ICLIHandler;
import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsChooseRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionChooseRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class CLIHandlerChooseRewardPickDevelopmentCard implements ICLIHandler
{
	private final Map<Integer, CardType> availableCardTypes = new HashMap<>();
	private final Map<Integer, List<ResourceAmount>> availableInstantDiscountChoices = new HashMap<>();
	private final Map<Integer, List<ResourceAmount>> availableDiscountChoices = new HashMap<>();
	private final Map<Integer, Row> availableRows = new HashMap<>();
	private final Map<Integer, ResourceCostOption> availableResourceCostOptions = new HashMap<>();
	private CardType cardType;
	private Row row;
	private Row instantRewardRow;
	private ResourceCostOption resourceCostOption;
	private List<ResourceAmount> instantDiscountChoice;
	private List<ResourceAmount> discountChoice;

	@Override
	public void execute()
	{
		this.showDevelopmentCardTypes();
		this.askCardType();
		this.showDevelopmentCards();
		this.askDevelopmentCard();
		if (!GameStatus.getInstance().getDevelopmentCards().get(this.cardType).get(GameStatus.getInstance().getCurrentDevelopmentCards().get(this.cardType).get(this.row)).getResourceCostOptions().isEmpty()) {
			this.showCostOptions();
			this.askCostOption();
			for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD)) {
				if ((((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType()) == this.cardType && (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getRow()) == this.row) {
					if (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getInstantDiscountChoices() != null) {
						this.showInstantDiscountChoices();
						this.askInstantDiscountChoice();
						if (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getDiscountChoices() != null) {
							this.showDiscountChoices();
							this.askDiscountChoice();
						}
					}
					break;
				}
			}
			this.askServants();
		}
	}

	@Override
	public CLIHandlerChooseRewardPickDevelopmentCard newInstance()
	{
		return new CLIHandlerChooseRewardPickDevelopmentCard();
	}

	private void showDevelopmentCardTypes()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Card Type Choice...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD)) {
			if (!this.availableCardTypes.containsValue(((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType())) {
				stringBuilder.append(Utils.createListElement(index, ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType().name()));
				this.availableCardTypes.put(index, (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType()));
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
		this.cardType = this.availableCardTypes.get(Integer.parseInt(input));
	}

	private void showDevelopmentCards()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Enter Development Card Choice...");
		int index = 1;
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD)) {
			if ((((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType()) == this.cardType) {
				stringBuilder.append('\n');
				stringBuilder.append(index);
				stringBuilder.append(" ==============\n");
				stringBuilder.append(GameStatus.getInstance().getDevelopmentCards().get(this.cardType).get(GameStatus.getInstance().getCurrentDevelopmentCards().get(this.cardType).get(((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getRow())).getInformations());
				this.availableRows.put(index, ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getRow());
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
		getInstantRewardRow(this.row);
	}

	private void getInstantRewardRow(Row row)
	{
		if (row == Row.THIRD) {
			this.instantRewardRow = Row.THIRD;
		} else if (row == Row.FOURTH) {
			this.instantRewardRow = Row.FOURTH;
		} else {
			this.instantRewardRow = null;
		}
	}

	private void showCostOptions()
	{
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD)) {
			if ((((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType()) == this.cardType && (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getRow()) == this.row) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Enter Cost Option Choice...");
				int index = 1;
				for (ResourceCostOption resourceCostOption : ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getResourceCostOptions()) {
					stringBuilder.append('\n');
					stringBuilder.append(index);
					stringBuilder.append(" ==============\n");
					if (!resourceCostOption.getRequiredResources().isEmpty()) {
						stringBuilder.append("\nRequired resources:\n");
						stringBuilder.append(ResourceAmount.getResourcesInformations(resourceCostOption.getRequiredResources(), true));
					}
					if (!resourceCostOption.getSpentResources().isEmpty()) {
						stringBuilder.append("\nSpent resources:\n");
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

	private void showInstantDiscountChoices()
	{
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD)) {
			if ((((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType()) == this.cardType && (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getRow()) == this.row) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Enter Instant Discount Choice...");
				int index = 1;
				for (List<ResourceAmount> resourceAmount : ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getInstantDiscountChoices()) {
					if (!resourceAmount.isEmpty()) {
						stringBuilder.append('\n');
						stringBuilder.append(index);
						stringBuilder.append(" ==============\n");
						stringBuilder.append("\nDiscount resources:\n");
						stringBuilder.append(ResourceAmount.getResourcesInformations(resourceAmount, true));
						stringBuilder.append("\n==============");
					}
					this.availableInstantDiscountChoices.put(index, resourceAmount);
					index++;
				}
				Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
				break;
			}
		}
	}

	private void askInstantDiscountChoice()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableInstantDiscountChoices.containsKey(Integer.parseInt(input)));
		this.instantDiscountChoice = this.availableInstantDiscountChoices.get(Integer.parseInt(input));
	}

	private void showDiscountChoices()
	{
		for (Serializable availableAction : GameStatus.getInstance().getCurrentAvailableActions().get(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD)) {
			if ((((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getCardType()) == this.cardType && (((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getRow()) == this.row) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("Enter Discount Choice...");
				int index = 1;
				for (List<ResourceAmount> resourceAmount : ((AvailableActionChooseRewardPickDevelopmentCard) availableAction).getDiscountChoices()) {
					if (!resourceAmount.isEmpty()) {
						stringBuilder.append('\n');
						stringBuilder.append(index);
						stringBuilder.append(" ==============\n");
						stringBuilder.append("\nDiscount resources:\n");
						stringBuilder.append(ResourceAmount.getResourcesInformations(resourceAmount, true));
						stringBuilder.append("\n==============");
					}
					this.availableDiscountChoices.put(index, resourceAmount);
					index++;
				}
				Client.getLogger().log(Level.INFO, "{0}", new Object[] { stringBuilder.toString() });
				break;
			}
		}
	}

	private void askDiscountChoice()
	{
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || !this.availableDiscountChoices.containsKey(Integer.parseInt(input)));
		this.discountChoice = this.availableDiscountChoices.get(Integer.parseInt(input));
	}

	private void askServants()
	{
		Client.getLogger().log(Level.INFO, "Enter Servant Amount...");
		String input;
		do {
			input = Client.getInstance().getCliScanner().nextLine();
		}
		while (!CommonUtils.isInteger(input) || Integer.parseInt(input) > GameStatus.getInstance().getCurrentPlayersData().get(GameStatus.getInstance().getOwnPlayerIndex()).getResourceAmounts().get(ResourceType.SERVANT));
		Client.getInstance().getConnectionHandler().sendGameAction(new ActionInformationsChooseRewardPickDevelopmentCard(Integer.parseInt(input), this.cardType, this.row, this.instantRewardRow, this.instantDiscountChoice, this.discountChoice, this.resourceCostOption));
	}
}
