package it.polimi.ingsw.lim.server.game.board;

import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ExcommunicationTile
{
	private final int index;
	private final String texturePath;
	private final Modifier modifier;

	public ExcommunicationTile(int index, String texturePath, Modifier modifier)
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

	public Modifier getModifier()
	{
		return this.modifier;
	}
}
