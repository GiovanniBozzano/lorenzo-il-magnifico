package it.polimi.ingsw.lim.server.enums;

public enum WorkSlotType
{
	SMALL(1),
	BIG(3);
	private final int cost;

	WorkSlotType(int cost)
	{
		this.cost = cost;
	}

	public int getCost()
	{
		return this.cost;
	}
}
