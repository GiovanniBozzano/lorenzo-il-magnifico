package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.ObjectInformations;

public abstract class CardInformations extends ObjectInformations
{
	private final String displayName;

	CardInformations(String texturePath, String displayName)
	{
		super(texturePath);
		this.displayName = displayName;
	}

	public abstract String getInformations();

	abstract String getCommonInformations();

	public String getDisplayName()
	{
		return this.displayName;
	}
}
