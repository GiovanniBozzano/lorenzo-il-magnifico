package it.polimi.ingsw.lim.common.game;

import java.io.Serializable;

public abstract class ObjectInformation implements Serializable
{
	private final String texturePath;

	protected ObjectInformation(String texturePath)
	{
		this.texturePath = texturePath;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}

	public abstract String getInformation();
}
