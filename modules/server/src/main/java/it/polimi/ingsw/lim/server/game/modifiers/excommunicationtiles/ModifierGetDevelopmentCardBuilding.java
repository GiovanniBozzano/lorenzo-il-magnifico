package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGetDevelopmentCardBuilding extends Modifier<EventGetDevelopmentCard>
{
	private static final ModifierGetDevelopmentCardBuilding INSTANCE = new ModifierGetDevelopmentCardBuilding();

	private ModifierGetDevelopmentCardBuilding()
	{
		super(EventGetDevelopmentCard.class);
	}

	@Override
	public void apply(EventGetDevelopmentCard event)
	{
	}

	public ModifierGetDevelopmentCardBuilding getInstance()
	{
		return ModifierGetDevelopmentCardBuilding.INSTANCE;
	}
}
