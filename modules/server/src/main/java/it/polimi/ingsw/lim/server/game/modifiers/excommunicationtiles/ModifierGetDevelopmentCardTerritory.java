package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGetDevelopmentCardTerritory extends Modifier<EventGetDevelopmentCard>
{
	private static final ModifierGetDevelopmentCardTerritory INSTANCE = new ModifierGetDevelopmentCardTerritory();

	private ModifierGetDevelopmentCardTerritory()
	{
		super(EventGetDevelopmentCard.class);
	}

	@Override
	public void apply(EventGetDevelopmentCard event)
	{
	}

	public ModifierGetDevelopmentCardTerritory getInstance()
	{
		return ModifierGetDevelopmentCardTerritory.INSTANCE;
	}
}
