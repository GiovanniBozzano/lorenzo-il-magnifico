package it.polimi.ingsw.lim.common;

import it.polimi.ingsw.lim.common.enums.Side;
import it.polimi.ingsw.lim.common.utils.CardHelper;

public abstract class Instance
{
	protected static Instance instance;
	protected Side side;
	protected CardHelper cardHelper;

	public Side getSide()
	{
		return side;
	}

	public CardHelper getCardHelper()
	{
		return cardHelper;
	}
}
