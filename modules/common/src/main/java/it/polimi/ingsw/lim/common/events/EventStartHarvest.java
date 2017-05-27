package it.polimi.ingsw.lim.common.events;

public class EventStartHarvest implements IEvent
{
	private final int value;

	public EventStartHarvest(int value)
	{
		this.value = value;
	}

	@Override
	public void apply()
	{
	}
}
