package it.polimi.ingsw.lim.common.enums;

/**
 * <p>This class is used to represent the type of row, with the corresponding
 * index value
 */
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
