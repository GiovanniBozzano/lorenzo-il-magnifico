package it.polimi.ingsw.lim.server.game.cards;

public class Card
{
	private final String displayName;
	private final int index;

	public Card(String displayName, int index)
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
