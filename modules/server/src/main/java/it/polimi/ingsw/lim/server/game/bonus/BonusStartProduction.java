package it.polimi.ingsw.lim.server.game.bonus;

import it.polimi.ingsw.lim.server.game.events.EventStartProduction;

public class BonusStartProduction extends Bonus<EventStartProduction>
{
	private final int value;

	public BonusStartProduction(int value)
	{
		super(EventStartProduction.class);
		this.value = value;
	}

	@Override
	public EventStartProduction apply(EventStartProduction event)
	{
		event.setValue(event.getValue() + this.value);
		return event;
	}

	public int getValue()
	{
		return this.value;
	}
}
