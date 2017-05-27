package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.events.EventStartHarvest;

public class BonusStartHarvest extends Bonus<EventStartHarvest>
{
	private final int value;

	public BonusStartHarvest(int value)
	{
		super(EventStartHarvest.class);
		this.value = value;
	}

	@Override
	public EventStartHarvest apply(EventStartHarvest event)
	{
		return event;
	}

	public int getValue()
	{
		return this.value;
	}
}
