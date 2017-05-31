package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionGetDevelopmentCard implements IAction
{
	private final Connection player;
	private final FamilyMemberType familyMemberType;
	private final int servants;
	private final CardType cardType;
	private final Row row;

	public ActionGetDevelopmentCard(Connection player, FamilyMemberType familyMemberType, int servants, CardType cardType, Row row)
	{
		this.player = player;
		this.familyMemberType = familyMemberType;
		this.servants = servants;
		this.cardType = cardType;
		this.row = row;
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

	public FamilyMemberType getFamilyMemberType()
	{
		return this.familyMemberType;
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
}
