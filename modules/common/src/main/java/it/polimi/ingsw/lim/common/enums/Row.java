package it.polimi.ingsw.lim.common.enums;

public enum Row
{
	FIRST(1),
	SECOND(3),
	THIRD(5),
	FOURTH(7);
	private final int value;

	Row(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
}
