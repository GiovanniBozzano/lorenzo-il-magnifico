package it.polimi.ingsw.lim.common.enums;

public enum Period
{
	FIRST,
	SECOND,
	THIRD;

	public static Period next(Period period)
	{
		if (period == null) {
			return FIRST;
		}
		switch (period) {
			case FIRST:
				return SECOND;
			case SECOND:
				return THIRD;
			default:
				return null;
		}
	}
}
