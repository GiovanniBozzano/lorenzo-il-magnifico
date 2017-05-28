package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.game.actions.ActionInformationsGetCard;
import it.polimi.ingsw.lim.server.network.Connection;

public class ActionGetCard extends ActionInformationsGetCard implements IAction
{
	private final Connection player;

	public ActionGetCard(Connection player, FamilyMemberType familyMemberType, int servants, CardType cardType, Row row)
	{
		super(familyMemberType, servants, cardType, row);
		this.player = player;
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
}
