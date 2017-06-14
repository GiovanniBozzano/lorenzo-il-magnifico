package it.polimi.ingsw.lim.common.enums;

public enum Row
{
	FIRST(0),
	SECOND(1),
	THIRD(2),
	FOURTH(3);
	private final int index;

	Row(int index)
	{
		this.index = index;
	}

	public int getIndex()
	{
		return this.index;
	}
}
