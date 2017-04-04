package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.utils.ResourceAmount;

public enum CardBlue
{
	BLUE_CARD_1_1("BLUE_CARD_1_1", Period.FIRST, new Object[] { new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 10), new ResourceAmount(Resource.STONE, 5) } }),
	BLUE_CARD_1_2("BLUE_CARD_1_2", Period.FIRST, new Object[] {}),
	BLUE_CARD_1_3("BLUE_CARD_1_3", Period.FIRST, new Object[] {}),
	BLUE_CARD_1_4("BLUE_CARD_1_4", Period.FIRST, new Object[] {}),
	BLUE_CARD_1_5("BLUE_CARD_1_5", Period.FIRST, new Object[] {}),
	BLUE_CARD_1_6("BLUE_CARD_1_6", Period.FIRST, new Object[] {}),
	BLUE_CARD_1_7("BLUE_CARD_1_7", Period.FIRST, new Object[] {}),
	BLUE_CARD_1_8("BLUE_CARD_1_8", Period.FIRST, new Object[] {}),
	BLUE_CARD_2_1("BLUE_CARD_2_1", Period.SECOND, new Object[] {}),
	BLUE_CARD_2_2("BLUE_CARD_2_2", Period.SECOND, new Object[] {}),
	BLUE_CARD_2_3("BLUE_CARD_2_3", Period.SECOND, new Object[] {}),
	BLUE_CARD_2_4("BLUE_CARD_2_4", Period.SECOND, new Object[] {}),
	BLUE_CARD_2_5("BLUE_CARD_2_5", Period.SECOND, new Object[] {}),
	BLUE_CARD_2_6("BLUE_CARD_2_6", Period.SECOND, new Object[] {}),
	BLUE_CARD_2_7("BLUE_CARD_2_7", Period.SECOND, new Object[] {}),
	BLUE_CARD_2_8("BLUE_CARD_2_8", Period.SECOND, new Object[] {}),
	BLUE_CARD_3_1("BLUE_CARD_3_1", Period.THIRD, new Object[] {}),
	BLUE_CARD_3_2("BLUE_CARD_3_2", Period.THIRD, new Object[] {}),
	BLUE_CARD_3_3("BLUE_CARD_3_3", Period.THIRD, new Object[] {}),
	BLUE_CARD_3_4("BLUE_CARD_3_4", Period.THIRD, new Object[] {}),
	BLUE_CARD_3_5("BLUE_CARD_3_5", Period.THIRD, new Object[] {}),
	BLUE_CARD_3_6("BLUE_CARD_3_6", Period.THIRD, new Object[] {}),
	BLUE_CARD_3_7("BLUE_CARD_3_7", Period.THIRD, new Object[] {}),
	BLUE_CARD_3_8("BLUE_CARD_3_8", Period.THIRD, new Object[] {});
	private String displayName;
	private Period period;
	private Object[] attributes;

	CardBlue(String displayName, Period period, Object[] attributes)
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
