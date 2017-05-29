package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGetCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGetDevelopmentCardTerritory extends Modifier<EventGetCard>
{
	private static final ModifierGetDevelopmentCardTerritory INSTANCE = new ModifierGetDevelopmentCardTerritory();

	private ModifierGetDevelopmentCardTerritory()
	{
		super(EventGetCard.class);
	}

	@Override
	public void apply(EventGetCard event)
	{
	}

	public ModifierGetDevelopmentCardTerritory getInstance()
	{
		return ModifierGetDevelopmentCardTerritory.INSTANCE;
	}
}
