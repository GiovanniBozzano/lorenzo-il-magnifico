package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;

public class AvailableActionGetDevelopmentCard extends AvailableAction
{
	private final CardType cardType;
	private final Row row;

	public AvailableActionGetDevelopmentCard(CardType cardType, Row row)
	{
		super(ActionType.GET_DEVELOPMENT_CARD);
		this.cardType = cardType;
		this.row = row;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public Row getRow()
	{
		return this.row;
	}
}