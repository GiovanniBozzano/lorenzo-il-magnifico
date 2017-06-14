package it.polimi.ingsw.lim.server.game.cards;

public abstract class Card
{
	private final String displayName;
	private final int index;

	Card(String displayName, int index)
	{
		this.displayName = displayName;
		this.index = index;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}

	public int getIndex()
	{
		return this.index;
	}
}
