package it.polimi.ingsw.lim.common.game;

import java.io.Serializable;

public class PersonalBonusTilesInformations implements Serializable
{
	private final int index;
	private final String texturePath;

	public PersonalBonusTilesInformations(int index, String texturePath)
	{
		this.index = index;
		this.texturePath = texturePath;
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}
}
