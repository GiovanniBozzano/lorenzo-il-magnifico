package it.polimi.ingsw.lim.server.game.actionrewards;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.actions.AvailableActionChooseRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.actions.ExpectedAction;
import it.polimi.ingsw.lim.common.game.actions.ExpectedActionChooseRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.game.utils.ResourceCostOption;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.actions.ActionChooseRewardPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;
import it.polimi.ingsw.lim.server.game.modifiers.ModifierPickDevelopmentCard;
import it.polimi.ingsw.lim.server.game.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ActionRewardPickDevelopmentCard extends ActionReward
{
	private final int value;
	private final List<CardType> cardTypes = new ArrayList<>();
	private final List<List<ResourceAmount>> instantDiscountChoices;

	public ActionRewardPickDevelopmentCard(String description, int value, List<CardType> cardTypes, List<List<ResourceAmount>> instantDiscountChoices)
	{
		super(description, ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD);
		this.value = value;
		this.cardTypes.addAll(cardTypes);
		this.instantDiscountChoices = new ArrayList<>(instantDiscountChoices);
	}

	@Override
	public ExpectedAction createExpectedAction(Player player)
	{
		List<AvailableActionChooseRewardPickDevelopmentCard> availableActions = new ArrayList<>();
		for (CardType cardType : this.cardTypes) {
			List<List<ResourceAmount>> discountChoices = new ArrayList<>();
			for (Modifier modifier : player.getActiveModifiers()) {
				if (modifier instanceof ModifierPickDevelopmentCard && ((ModifierPickDevelopmentCard) modifier).getCardType() == cardType && !((ModifierPickDevelopmentCard) modifier).getDiscountChoices().isEmpty()) {
					discountChoices.addAll(((ModifierPickDevelopmentCard) modifier).getDiscountChoices());
				}
			}
			for (Row row : Row.values()) {
				if (player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(cardType).get(row) == null) {
					continue;
				}
				List<ResourceCostOption> availableResourceCostOptions = new ArrayList<>();
				List<List<ResourceAmount>> availableInstantDiscountChoices = new ArrayList<>();
				List<List<ResourceAmount>> availableDiscountChoises = new ArrayList<>();
				boolean validCard = false;
				if (player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(cardType).get(row).getResourceCostOptions().isEmpty()) {
					try {
						new ActionChooseRewardPickDevelopmentCard(player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, Row.FOURTH, new ArrayList<>(), new ArrayList<>(), null, player).isLegal();
						validCard = true;
					} catch (GameActionFailedException exception) {
						Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
					}
				} else {
					for (ResourceCostOption resourceCostOption : player.getRoom().getGameHandler().getCardsHandler().getCurrentDevelopmentCards().get(cardType).get(row).getResourceCostOptions()) {
						if (this.instantDiscountChoices.isEmpty()) {
							if (discountChoices.isEmpty()) {
								try {
									new ActionChooseRewardPickDevelopmentCard(player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, Row.FOURTH, new ArrayList<>(), new ArrayList<>(), resourceCostOption, player).isLegal();
									validCard = true;
									if (!availableResourceCostOptions.contains(resourceCostOption)) {
										availableResourceCostOptions.add(resourceCostOption);
									}
								} catch (GameActionFailedException exception) {
									Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
								}
							} else {
								for (List<ResourceAmount> discountChoice : discountChoices) {
									try {
										new ActionChooseRewardPickDevelopmentCard(player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, Row.FOURTH, new ArrayList<>(), discountChoice, resourceCostOption, player).isLegal();
										validCard = true;
										if (!availableResourceCostOptions.contains(resourceCostOption)) {
											availableResourceCostOptions.add(resourceCostOption);
										}
										if (!availableDiscountChoises.contains(discountChoice)) {
											availableDiscountChoises.add(discountChoice);
										}
									} catch (GameActionFailedException exception) {
										Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
									}
								}
							}
						} else {
							if (discountChoices.isEmpty()) {
								for (List<ResourceAmount> instantDiscountChoice : this.instantDiscountChoices) {
									try {
										new ActionChooseRewardPickDevelopmentCard(player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, Row.FOURTH, instantDiscountChoice, new ArrayList<>(), resourceCostOption, player).isLegal();
										validCard = true;
										if (!availableResourceCostOptions.contains(resourceCostOption)) {
											availableResourceCostOptions.add(resourceCostOption);
										}
										if (!availableInstantDiscountChoices.contains(instantDiscountChoice)) {
											availableInstantDiscountChoices.add(instantDiscountChoice);
										}
									} catch (GameActionFailedException exception) {
										Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
									}
								}
							} else {
								for (List<ResourceAmount> instantDiscountChoice : this.instantDiscountChoices) {
									for (List<ResourceAmount> discountChoice : discountChoices) {
										try {
											new ActionChooseRewardPickDevelopmentCard(player.getPlayerResourceHandler().getResources().get(ResourceType.SERVANT), cardType, row, Row.FOURTH, instantDiscountChoice, discountChoice, resourceCostOption, player).isLegal();
											validCard = true;
											if (!availableResourceCostOptions.contains(resourceCostOption)) {
												availableResourceCostOptions.add(resourceCostOption);
											}
											if (!availableInstantDiscountChoices.contains(instantDiscountChoice)) {
												availableInstantDiscountChoices.add(instantDiscountChoice);
											}
											if (!availableDiscountChoises.contains(discountChoice)) {
												availableDiscountChoises.add(discountChoice);
											}
										} catch (GameActionFailedException exception) {
											Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
										}
									}
								}
							}
						}
					}
				}
				if (validCard) {
					availableActions.add(new AvailableActionChooseRewardPickDevelopmentCard(cardType, row, availableResourceCostOptions, availableInstantDiscountChoices, availableDiscountChoises));
				}
			}
		}
		if (!availableActions.isEmpty()) {
			return new ExpectedActionChooseRewardPickDevelopmentCard(availableActions);
		}
		return null;
	}

	public int getValue()
	{
		return this.value;
	}

	public List<List<ResourceAmount>> getInstantDiscountChoices()
	{
		return this.instantDiscountChoices;
	}
}
