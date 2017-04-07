package it.polimi.ingsw.lim.common.enums;

public enum ExcommunicationTiles
{
	EXCOMMUNICATION_TILES_1_1(Period.FIRST, new Object[] {}),
	EXCOMMUNICATION_TILES_1_2(Period.FIRST, new Object[] {}),
	EXCOMMUNICATION_TILES_1_3(Period.FIRST, new Object[] {}),
	EXCOMMUNICATION_TILES_1_4(Period.FIRST, new Object[] {}),
	EXCOMMUNICATION_TILES_1_5(Period.FIRST, new Object[] {}),
	EXCOMMUNICATION_TILES_1_6(Period.FIRST, new Object[] {}),
	EXCOMMUNICATION_TILES_1_7(Period.FIRST, new Object[] {}),
	EXCOMMUNICATION_TILES_2_1(Period.SECOND, new Object[] {}),
	EXCOMMUNICATION_TILES_2_2(Period.SECOND, new Object[] {}),
	EXCOMMUNICATION_TILES_2_3(Period.SECOND, new Object[] {}),
	EXCOMMUNICATION_TILES_2_4(Period.SECOND, new Object[] {}),
	EXCOMMUNICATION_TILES_2_5(Period.SECOND, new Object[] {}),
	EXCOMMUNICATION_TILES_2_6(Period.SECOND, new Object[] {}),
	EXCOMMUNICATION_TILES_2_7(Period.SECOND, new Object[] {}),
	EXCOMMUNICATION_TILES_3_1(Period.THIRD, new Object[] {}),
	EXCOMMUNICATION_TILES_3_2(Period.THIRD, new Object[] {}),
	EXCOMMUNICATION_TILES_3_3(Period.THIRD, new Object[] {}),
	EXCOMMUNICATION_TILES_3_4(Period.THIRD, new Object[] {}),
	EXCOMMUNICATION_TILES_3_5(Period.THIRD, new Object[] {}),
	EXCOMMUNICATION_TILES_3_6(Period.THIRD, new Object[] {}),
	EXCOMMUNICATION_TILES_3_7(Period.THIRD, new Object[] {});
	private final Period period;
	private final Object[] attributes;

	ExcommunicationTiles(Period period, Object[] attributes)
	{
		this.period = period;
		this.attributes = attributes;
	}

	public Period getPeriod()
	{
		return this.period;
	}

	public Object[] getAttributes()
	{
		return this.attributes;
	}
}
