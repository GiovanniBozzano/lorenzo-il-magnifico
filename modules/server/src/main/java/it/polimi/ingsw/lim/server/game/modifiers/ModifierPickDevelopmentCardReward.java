package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.Row;
import it.polimi.ingsw.lim.server.game.events.EventPickDevelopmentCard;

import java.util.ArrayList;
import java.util.List;

public class ModifierPickDevelopmentCardReward extends Modifier<EventPickDevelopmentCard>
{
	private final List<Row> rows;

	public ModifierPickDevelopmentCardReward(String description, List<Row> rows)
	{
		super(EventPickDevelopmentCard.class, description);
		this.rows = new ArrayList<>(rows);
	}

	@Override
	public void apply(EventPickDevelopmentCard event)
	{
		if (this.rows.contains(event.getRow())) {
			event.setGetBoardPositionReward(false);
		}
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventPickDevelopmentCard.class);
	}
}
