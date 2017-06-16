package it.polimi.ingsw.lim.common.game.board;

import it.polimi.ingsw.lim.common.game.ObjectInformations;

public class ExcommunicationTileInformations extends ObjectInformations
{
	private final String modifier;

	public ExcommunicationTileInformations(int index, String texturePath, String modifier)
	{
		super(index, texturePath);
		this.modifier = modifier;
	}

	public String getModifier()
	{
		return this.modifier;
	}
}
