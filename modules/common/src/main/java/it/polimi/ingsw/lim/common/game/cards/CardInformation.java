package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.ObjectInformation;

public abstract class CardInformation extends ObjectInformation
{
	private final String displayName;

	CardInformation(String texturePath, String displayName)
	{
		super(texturePath);
		this.displayName = displayName;
	}

	abstract String getCommonInformation();

	public String getDisplayName()
	{
		return this.displayName;
	}
}
