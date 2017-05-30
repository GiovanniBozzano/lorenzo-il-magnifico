package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.network.Connection;

public class EventGetDevelopmentCard extends Event
{
	private final CardType cardType;
	private final Row row;
	private final ResourceAmount[] cost;
	private int actionValue;
	private boolean ignoreSlotLock = false;

	public EventGetDevelopmentCard(Connection player, CardType cardType, Row row, ResourceAmount[] cost, int actionValue)
	{
		super(player);
		this.cardType = cardType;
		this.row = row;
		this.cost = cost;
		this.actionValue = actionValue;
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

	public int getActionValue()
	{
		return this.actionValue;
	}

	public void setActionValue(int actionValue)
	{
		this.actionValue = actionValue <= 0 ? 0 : actionValue;
	}

	public boolean isIgnoreSlotLock()
	{
		return this.ignoreSlotLock;
	}

	public void setIgnoreSlotLock(boolean ignoreSlotLock)
	{
		this.ignoreSlotLock = ignoreSlotLock;
	}
}
