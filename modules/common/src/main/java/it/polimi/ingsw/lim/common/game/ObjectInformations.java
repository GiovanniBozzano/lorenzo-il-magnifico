package it.polimi.ingsw.lim.common.game;

import java.io.Serializable;

public class ObjectInformations implements Serializable
{
	private final String texturePath;

	public ObjectInformations(String texturePath)
	{
		this.texturePath = texturePath;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}
}
