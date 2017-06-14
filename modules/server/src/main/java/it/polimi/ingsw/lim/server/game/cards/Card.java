package it.polimi.ingsw.lim.server.game.cards;

public abstract class Card
{
	private final int index;
	private final String displayName;

	Card(int index, String displayName)
	{
		this.index = index;
		this.displayName = displayName;
	}

	public int getIndex()
	{
		return this.index;
	}

	public String getDisplayName()
	{
		return this.displayName;
	}
}
