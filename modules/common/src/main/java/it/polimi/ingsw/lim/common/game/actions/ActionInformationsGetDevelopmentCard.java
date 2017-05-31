package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.Row;

public class ActionInformationsGetDevelopmentCard extends ActionInformations
{
	private final FamilyMemberType familyMemberType;
	private final int servants;
	private final CardType cardType;
	private final Row row;

	public ActionInformationsGetDevelopmentCard(FamilyMemberType familyMemberType, int servants, CardType cardType, Row row)
	{
		super(ActionType.GET_DEVELOPMENT_CARD);
		this.familyMemberType = familyMemberType;
		this.servants = servants;
		this.cardType = cardType;
		this.row = row;
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
