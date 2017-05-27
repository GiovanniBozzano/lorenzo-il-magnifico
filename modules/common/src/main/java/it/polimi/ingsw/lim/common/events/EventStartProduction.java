package it.polimi.ingsw.lim.common.events;

public class EventStartProduction implements IEvent
{
	private int value;

	public EventStartProduction(int value)
	{
		this.value = value;
	}

	@Override
	public void apply()
	{
	}

	public int getValue()
	{
		return this.value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}
}
