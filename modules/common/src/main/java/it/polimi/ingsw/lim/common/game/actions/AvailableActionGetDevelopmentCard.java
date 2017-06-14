package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;

import java.util.List;

public class AvailableActionGetDevelopmentCard extends AvailableAction
{
	private final CardType cardType;
	private final Row row;
	private final List<List<ResourceAmount>> discountChoices;

	public AvailableActionGetDevelopmentCard(CardType cardType, Row row, List<List<ResourceAmount>> discountChoices)
	{
		super(ActionType.GET_DEVELOPMENT_CARD);
		this.cardType = cardType;
		this.row = row;
		this.discountChoices = discountChoices;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public Row getRow()
	{
		return this.row;
	}

	public List<List<ResourceAmount>> getDiscountChoices()
	{
		return this.discountChoices;
	}
}