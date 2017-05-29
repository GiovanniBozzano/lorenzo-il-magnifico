package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGetCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGetDevelopmentCardBuilding extends Modifier<EventGetCard>
{
	private static final ModifierGetDevelopmentCardBuilding INSTANCE = new ModifierGetDevelopmentCardBuilding();

	private ModifierGetDevelopmentCardBuilding()
	{
		super(EventGetCard.class);
	}

	@Override
	public void apply(EventGetCard event)
	{
	}

	public ModifierGetDevelopmentCardBuilding getInstance()
	{
		return ModifierGetDevelopmentCardBuilding.INSTANCE;
	}
}
