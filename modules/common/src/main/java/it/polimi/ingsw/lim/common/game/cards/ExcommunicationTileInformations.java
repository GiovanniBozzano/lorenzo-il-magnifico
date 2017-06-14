package it.polimi.ingsw.lim.common.game.cards;

import java.io.Serializable;

public class ExcommunicationTileInformations implements Serializable
{
	private final int index;
	private final String texturePath;
	private final String description;

	public ExcommunicationTileInformations(int index, String texturePath, String description)
	{
		this.index = index;
		this.texturePath = texturePath;
		this.description = description;
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}

	public String getDescription()
	{
		return this.description;
	}
}
