package it.polimi.ingsw.lim.common.enums;

public enum CardYellow
{
	YELLOW_CARD_1_1("YELLOW_CARD_1_1", Period.FIRST, new Object[] {}),
	YELLOW_CARD_1_2("YELLOW_CARD_1_2", Period.FIRST, new Object[] {}),
	YELLOW_CARD_1_3("YELLOW_CARD_1_3", Period.FIRST, new Object[] {}),
	YELLOW_CARD_1_4("YELLOW_CARD_1_4", Period.FIRST, new Object[] {}),
	YELLOW_CARD_1_5("YELLOW_CARD_1_5", Period.FIRST, new Object[] {}),
	YELLOW_CARD_1_6("YELLOW_CARD_1_6", Period.FIRST, new Object[] {}),
	YELLOW_CARD_1_7("YELLOW_CARD_1_7", Period.FIRST, new Object[] {}),
	YELLOW_CARD_1_8("YELLOW_CARD_1_8", Period.FIRST, new Object[] {}),
	YELLOW_CARD_2_1("YELLOW_CARD_2_1", Period.SECOND, new Object[] {}),
	YELLOW_CARD_2_2("YELLOW_CARD_2_2", Period.SECOND, new Object[] {}),
	YELLOW_CARD_2_3("YELLOW_CARD_2_3", Period.SECOND, new Object[] {}),
	YELLOW_CARD_2_4("YELLOW_CARD_2_4", Period.SECOND, new Object[] {}),
	YELLOW_CARD_2_5("YELLOW_CARD_2_5", Period.SECOND, new Object[] {}),
	YELLOW_CARD_2_6("YELLOW_CARD_2_6", Period.SECOND, new Object[] {}),
	YELLOW_CARD_2_7("YELLOW_CARD_2_7", Period.SECOND, new Object[] {}),
	YELLOW_CARD_2_8("YELLOW_CARD_2_8", Period.SECOND, new Object[] {}),
	YELLOW_CARD_3_1("YELLOW_CARD_3_1", Period.THIRD, new Object[] {}),
	YELLOW_CARD_3_2("YELLOW_CARD_3_2", Period.THIRD, new Object[] {}),
	YELLOW_CARD_3_3("YELLOW_CARD_3_3", Period.THIRD, new Object[] {}),
	YELLOW_CARD_3_4("YELLOW_CARD_3_4", Period.THIRD, new Object[] {}),
	YELLOW_CARD_3_5("YELLOW_CARD_3_5", Period.THIRD, new Object[] {}),
	YELLOW_CARD_3_6("YELLOW_CARD_3_6", Period.THIRD, new Object[] {}),
	YELLOW_CARD_3_7("YELLOW_CARD_3_7", Period.THIRD, new Object[] {}),
	YELLOW_CARD_3_8("YELLOW_CARD_3_8", Period.THIRD, new Object[] {});
	private String displayName;
	private Period period;
	private Object[] attributes;

	CardYellow(String displayName, Period period, Object[] attributes)
	{
		this.displayName = displayName;
		this.period = period;
		this.attributes = attributes;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public Period getPeriod()
	{
		return period;
	}

	public Object[] getAttributes()
	{
		return attributes;
	}
}
