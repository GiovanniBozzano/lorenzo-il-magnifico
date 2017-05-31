package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionChooseRewardGetDevelopmentCard implements IAction
{
	private final Connection player;
	private final int servants;
	private final CardType cardType;
	private final Row row;
	private final Row instantRewardRow;

	public ActionChooseRewardGetDevelopmentCard(Connection player, int servants, CardType cardType, Row row, Row instantRewardRow)
	{
		this.player = player;
		this.servants = servants;
		this.cardType = cardType;
		this.row = row;
		this.instantRewardRow = instantRewardRow;
	}

	@Override
	public boolean isLegal()
	{
		return false;
	}

	@Override
	public void apply()
	{
	}

	@Override
	public Connection getPlayer()
	{
		return this.player;
	}

	public int getServants()
	{
		return this.servants;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public Row getRow()
	{
		return this.row;
	}

	public Row getInstantRewardRow()
	{
		return this.instantRewardRow;
	}
}
