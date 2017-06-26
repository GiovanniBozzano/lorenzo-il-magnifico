package it.polimi.ingsw.lim.server.game.events;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.player.Player;

import java.util.ArrayList;
import java.util.List;

public class EventPickDevelopmentCard extends Event
{
	private final CardType cardType;
	private final Row row;
	private final List<ResourceAmount> cost = new ArrayList<>();
	private int actionValue;
	private boolean ignoreTerritoriesSlotLock = false;
	private boolean getBoardPositionReward = true;

	public EventPickDevelopmentCard(Player player, CardType cardType, Row row, List<ResourceAmount> cost, int actionValue)
	{
		super(player);
		this.cardType = cardType;
		this.row = row;
		if (cost != null) {
			this.cost.addAll(cost);
		}
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

	public List<ResourceAmount> getResourceCost()
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

	public boolean isIgnoreTerritoriesSlotLock()
	{
		return this.ignoreTerritoriesSlotLock;
	}

	public void setIgnoreTerritoriesSlotLock(boolean ignoreSlotLock)
	{
		this.ignoreTerritoriesSlotLock = ignoreSlotLock;
	}

	public boolean isGetBoardPositionReward()
	{
		return this.getBoardPositionReward;
	}

	public void setGetBoardPositionReward(boolean getBoardPositionReward)
	{
		this.getBoardPositionReward = getBoardPositionReward;
	}
}
