package it.polimi.ingsw.lim.common.game.board;

import it.polimi.ingsw.lim.common.game.ObjectInformations;

public class ExcommunicationTileInformations extends ObjectInformations
{
	private final String modifier;

	public ExcommunicationTileInformations(String texturePath, String modifier)
	{
		super(texturePath);
		this.modifier = modifier;
	}

	public String getModifier()
	{
		return this.modifier;
	}
}
