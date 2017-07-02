package it.polimi.ingsw.lim.common.game;

import java.io.Serializable;

public abstract class ObjectInformations implements Serializable
{
	private final String texturePath;

	protected ObjectInformations(String texturePath)
	{
		this.texturePath = texturePath;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}

	public abstract String getInformations();
}
