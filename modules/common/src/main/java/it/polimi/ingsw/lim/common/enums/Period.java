package it.polimi.ingsw.lim.common.enums;

public enum Period
{
	FIRST,
	SECOND,
	THIRD;

	public static Period next(Period period)
	{
		if (period == null) {
			return Period.FIRST;
		}
		switch (period) {
			case FIRST:
				return Period.SECOND;
			case SECOND:
				return Period.THIRD;
			default:
				return null;
		}
	}
}
