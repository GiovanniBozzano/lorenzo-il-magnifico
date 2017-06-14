package it.polimi.ingsw.lim.common.game.cards;

import java.io.Serializable;

public class ExcommunicationTileInformations implements Serializable
{
	private final int index;
	private final String texturePath;
	private final String modifier;

	public ExcommunicationTileInformations(int index, String texturePath, String modifier)
	{
		this.index = index;
		this.texturePath = texturePath;
		this.modifier = modifier;
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}

	public String getModifier()
	{
		return this.modifier;
	}
}
