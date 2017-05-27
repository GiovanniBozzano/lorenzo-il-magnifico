package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.common.events.EventGetCard;

public class BonusMalus extends Bonus<EventGetCard>
{
	private final Row[] rows;

	public BonusMalus(Row[] rows)
	{
		super(EventGetCard.class);
		this.rows = rows;
	}

	@Override
	public EventGetCard apply(EventGetCard event)
	{
		return event;
	}

	public Row[] getRows()
	{
		return this.rows;
	}
}
