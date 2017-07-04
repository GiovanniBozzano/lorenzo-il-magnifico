package it.polimi.ingsw.lim.common.game.board;

import it.polimi.ingsw.lim.common.game.ObjectInformation;

public class ExcommunicationTileInformation extends ObjectInformation
{
	private final String modifier;

	public ExcommunicationTileInformation(String texturePath, String modifier)
	{
		super(texturePath);
		this.modifier = modifier;
	}

	@Override
	public String getInformation()
	{
		return this.modifier;
	}
}
