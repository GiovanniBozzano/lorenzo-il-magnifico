package it.polimi.ingsw.lim.common.enums;

/**
 * <p>This class is used to represent the type of period
 */
public enum Period
{
	FIRST,
	SECOND,
	THIRD;
	/**
	 * <p>Uses current period to determine the next one.
	 *
	 * @param period the current period.
	 *
	 * @return the next period.
	 *
	 */
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
