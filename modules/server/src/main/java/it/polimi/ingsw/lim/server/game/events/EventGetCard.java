package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;

public class EventGetCard implements IEvent
{
	private final CardType cardType;
	private final Row row;
	private final ResourceAmount[] cost;

	public EventGetCard(CardType cardType, Row row, ResourceAmount[] cost)
	{
		this.cardType = cardType;
		this.row = row;
		this.cost = cost;
	}

	@Override
	public void apply()
	{
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public Row getRow()
	{
		return this.row;
	}

	public ResourceAmount[] getCost()
	{
		return this.cost;
	}
}
