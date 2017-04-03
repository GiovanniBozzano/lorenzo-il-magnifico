package it.polimi.ingsw.lim.common;

import it.polimi.ingsw.lim.common.utils.CardHelper;
import it.polimi.ingsw.lim.common.enums.EnumSide;

public abstract class Instance
{
	protected static Instance instance;
	protected EnumSide side;
	protected CardHelper cardHelper;

	public static Instance getInstance()
	{
		return instance;
	}

	public EnumSide getSide()
	{
		return side;
	}

	public CardHelper getCardHelper()
	{
		return cardHelper;
	}
}
