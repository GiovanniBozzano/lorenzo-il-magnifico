package it.polimi.ingsw.lim.common.enums;

public enum Row
{
	FIRST(0, 1),
	SECOND(1, 3),
	THIRD(2, 5),
	FOURTH(3, 7);
	private final int index;
	private final int value;

	Row(int index, int value)
	{
		this.index = index;
		this.value = value;
	}

	public static Row getFromIndex(int index)
	{
		for (Row row : Row.values()) {
			if (row.getIndex() == index) {
				return row;
			}
		}
		return null;
	}

	public int getIndex()
	{
		return this.index;
	}

	public int getValue()
	{
		return this.value;
	}
}
