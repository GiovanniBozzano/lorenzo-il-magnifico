package it.polimi.ingsw.lim.common.enums;

import it.polimi.ingsw.lim.common.utils.ResourceAmount;

public enum CardBlue
{
	CONDOTTIERO("BLUE_CARD_1_1", Period.FIRST, new Object[] { new ResourceAmount[] { new ResourceAmount(Resource.WOOD, 10), new ResourceAmount(Resource.STONE, 5) } }),
	COSTRUTTORE("BLUE_CARD_1_2", Period.FIRST, new Object[] {}),
	DAMA("BLUE_CARD_1_3", Period.FIRST, new Object[] {}),
	CAVALIERE("BLUE_CARD_1_4", Period.FIRST, new Object[] {}),
	CONTADINO("BLUE_CARD_1_5", Period.FIRST, new Object[] {}),
	ARTIGIANO("BLUE_CARD_1_6", Period.FIRST, new Object[] {}),
	PREDICATORE("BLUE_CARD_1_7", Period.FIRST, new Object[] {}),
	BADESSA("BLUE_CARD_1_8", Period.FIRST, new Object[] {}),
	CAPITANO("BLUE_CARD_2_1", Period.SECOND, new Object[] {}),
	ARCHITETTO("BLUE_CARD_2_2", Period.SECOND, new Object[] {}),
	MECENATE("BLUE_CARD_2_3", Period.SECOND, new Object[] {}),
	EROE("BLUE_CARD_2_4", Period.SECOND, new Object[] {}),
	FATTORE("BLUE_CARD_2_5", Period.SECOND, new Object[] {}),
	STUDIOSO("BLUE_CARD_2_6", Period.SECOND, new Object[] {}),
	MESSO_PAPALE("BLUE_CARD_2_7", Period.SECOND, new Object[] {}),
	MESSO_REALE("BLUE_CARD_2_8", Period.SECOND, new Object[] {}),
	NOBILE("BLUE_CARD_3_1", Period.THIRD, new Object[] {}),
	GOVERNATORE("BLUE_CARD_3_2", Period.THIRD, new Object[] {}),
	CORTIGIANA("BLUE_CARD_3_3", Period.THIRD, new Object[] {}),
	ARALDO("BLUE_CARD_3_4", Period.THIRD, new Object[] {}),
	CARDINALE("BLUE_CARD_3_5", Period.THIRD, new Object[] {}),
	VESCOVO("BLUE_CARD_3_6", Period.THIRD, new Object[] {}),
	GENERALE("BLUE_CARD_3_7", Period.THIRD, new Object[] {}),
	AMBASCIATORE("BLUE_CARD_3_8", Period.THIRD, new Object[] {});
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
