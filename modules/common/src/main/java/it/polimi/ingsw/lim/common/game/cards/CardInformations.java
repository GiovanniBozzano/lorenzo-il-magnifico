package it.polimi.ingsw.lim.common.game.cards;

import it.polimi.ingsw.lim.common.game.ObjectInformations;

public abstract class CardInformations extends ObjectInformations
{
	private final String displayName;

	CardInformations(int index, String texturePath, String displayName)
	{
		super(index, texturePath);
		this.displayName = displayName;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}
}
