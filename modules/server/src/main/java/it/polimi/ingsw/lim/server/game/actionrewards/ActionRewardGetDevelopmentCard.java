package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionChooseRewardGetDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardGetDevelopmentCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.actions.ActionChooseRewardGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.modifiers.ModifierGetDevelopmentCard;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ActionRewardGetDevelopmentCard extends ActionReward
{
	private final Map<CardType, Row> maximumRows;
	private final List<List<ResourceAmount>> instantDiscountChoices;

	public ActionRewardGetDevelopmentCard(String description, Map<CardType, Row> maximumRows, List<List<ResourceAmount>> instantDiscountChoices)
	{
		super(description, ActionType.CHOOSE_REWARD_GET_DEVELOPMENT_CARD);
		this.maximumRows = new EnumMap<>(maximumRows);
		this.instantDiscountChoices = new ArrayList<>(instantDiscountChoices);
	}

	@Override
	public ExpectedAction createExpectedAction(GameHandler gameHandler, Connection player)
	{
		List<AvailableActionChooseRewardGetDevelopmentCard> availableActions = new ArrayList<>();
		List<List<ResourceAmount>> discountChoices = new ArrayList<>();
		for (Modifier modifier : player.getPlayerHandler().getActiveModifiers()) {
			if (modifier.getEventClass() == EventGetDevelopmentCard.class) {
				discountChoices.addAll(((ModifierGetDevelopmentCard) modifier).getDiscountChoices());
			}
		}
		for (Entry<CardType, Row> entry : this.maximumRows.entrySet()) {
			List<Row> rows = new ArrayList<>();
			for (Row row : Row.values()) {
				if (row.getIndex() <= entry.getValue().getIndex()) {
					rows.add(row);
				}
			}
			for (Row row : rows) {
				boolean validCard = false;
				List<ResourceCostOption> availableResourceCostOptions = new ArrayList<>();
				List<List<ResourceAmount>> availableInstantDiscountChoices = new ArrayList<>();
				List<List<ResourceAmount>> availableDiscountChoises = new ArrayList<>();
				if (gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(entry.getKey()).get(row).getResourceCostOptions().isEmpty()) {
					if (new ActionChooseRewardGetDevelopmentCard(player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), entry.getKey(), row, Row.FOURTH, null, null, null, player).isLegal()) {
						validCard = true;
					}
				} else {
					for (ResourceCostOption resourceCostOption : gameHandler.getCardsHandler().getCurrentDevelopmentCards().get(entry.getKey()).get(row).getResourceCostOptions()) {
						if (this.instantDiscountChoices.isEmpty()) {
							if (discountChoices.isEmpty()) {
								if (new ActionChooseRewardGetDevelopmentCard(player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), entry.getKey(), row, Row.FOURTH, null, null, resourceCostOption, player).isLegal()) {
									validCard = true;
								}
							} else {
								for (List<ResourceAmount> discountChoice : discountChoices) {
									if (new ActionChooseRewardGetDevelopmentCard(player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), entry.getKey(), row, Row.FOURTH, null, discountChoice, resourceCostOption, player).isLegal()) {
										validCard = true;
										if (!availableDiscountChoises.contains(discountChoice)) {
											availableDiscountChoises.add(discountChoice);
										}
									}
								}
							}
						} else {
							if (discountChoices.isEmpty()) {
								for (List<ResourceAmount> instantDiscountChoice : this.instantDiscountChoices) {
									if (new ActionChooseRewardGetDevelopmentCard(player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), entry.getKey(), row, Row.FOURTH, instantDiscountChoice, null, resourceCostOption, player).isLegal()) {
										validCard = true;
										if (!availableInstantDiscountChoices.contains(instantDiscountChoice)) {
											availableInstantDiscountChoices.add(instantDiscountChoice);
										}
									}
								}
							} else {
								for (List<ResourceAmount> instantDiscountChoice : this.instantDiscountChoices) {
									for (List<ResourceAmount> discountChoice : discountChoices) {
										if (new ActionChooseRewardGetDevelopmentCard(player.getPlayerHandler().getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), entry.getKey(), row, Row.FOURTH, instantDiscountChoice, discountChoice, resourceCostOption, player).isLegal()) {
											validCard = true;
											if (!availableInstantDiscountChoices.contains(instantDiscountChoice)) {
												availableInstantDiscountChoices.add(instantDiscountChoice);
											}
											if (!availableDiscountChoises.contains(discountChoice)) {
												availableDiscountChoises.add(discountChoice);
											}
										}
									}
								}
							}
						}
						if (validCard) {
							if (!availableResourceCostOptions.contains(resourceCostOption)) {
								availableResourceCostOptions.add(resourceCostOption);
							}
						}
					}
				}
				if (validCard) {
					availableActions.add(new AvailableActionChooseRewardGetDevelopmentCard(entry.getKey(), row, availableResourceCostOptions, availableInstantDiscountChoices, availableDiscountChoises));
				}
			}
		}
		if (!availableActions.isEmpty()) {
			return new ExpectedActionChooseRewardGetDevelopmentCard(this.maximumRows, availableActions, this.instantDiscountChoices);
		}
		return null;
	}

	public List<List<ResourceAmount>> getInstantDiscountChoices()
	{
		return this.instantDiscountChoices;
	}
}
