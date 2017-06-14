package it.polimi.ingsw.lim.common.game.cards;

import java.io.Serializable;

public abstract class CardInformations implements Serializable
{
	private final int index;
	private final String texturePath;
	private final String displayName;

	CardInformations(int index, String texturePath, String displayName)
	{
		this.index = index;
		this.texturePath = texturePath;
		this.displayName = displayName;
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}
}
