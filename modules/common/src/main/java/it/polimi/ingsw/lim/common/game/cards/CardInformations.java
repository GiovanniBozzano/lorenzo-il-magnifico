package it.polimi.ingsw.lim.common.game.cards;

import java.io.Serializable;

public abstract class CardInformations implements Serializable
{
	private final int index;
	private final String displayName;
	private final String texturePath;

	CardInformations(int index, String displayName, String texturePath)
	{
		this.index = index;
		this.displayName = displayName;
		this.texturePath = texturePath;
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}
}
