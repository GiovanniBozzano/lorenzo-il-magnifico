package it.polimi.ingsw.lim.server.game.cards;

/**
 * <p>This class represents a server side generic card. It is used to store
 * common information.
 */
public abstract class Card
{
	private final int index;
	private final String texturePath;
	private final String displayName;

	Card(int index, String texturePath, String displayName)
	{
		this.index = index;
		this.texturePath = texturePath;
		this.displayName = displayName;
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getTexturePath()
	{
		return this.texturePath;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}
}
