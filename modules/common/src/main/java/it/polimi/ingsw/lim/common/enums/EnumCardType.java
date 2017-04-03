package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.cards.*;

public enum EnumCardType
{
	GREEN(CardGreen.class),
	YELLOW(CardYellow.class),
	BLUE(CardBlue.class),
	PURPLE(CardPurple.class);
	private Class<? extends Card> type;

	EnumCardType(Class<? extends Card> type)
	{
		this.type = type;
	}
}
