package it.polimi.ingsw.lim.common.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.Row;

public class ActionInformationsChooseRewardGetDevelopmentCard extends ActionInformations
{
	private final int servants;
	private final CardType cardType;
	private final Row row;
	private final Row instantRewardRow;

	public ActionInformationsChooseRewardGetDevelopmentCard(int servants, CardType cardType, Row row, Row instantRewardRow)
	{
		super(ActionType.CHOOSE_REWARD_GET_DEVELOPMENT_CARD);
		this.servants = servants;
		this.cardType = cardType;
		this.row = row;
		this.instantRewardRow = instantRewardRow;
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
