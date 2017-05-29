package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGetCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGetDevelopmentCardVenture extends Modifier<EventGetCard>
{
	private static final ModifierGetDevelopmentCardVenture INSTANCE = new ModifierGetDevelopmentCardVenture();

	private ModifierGetDevelopmentCardVenture()
	{
		super(EventGetCard.class);
	}

	@Override
	public void apply(EventGetCard event)
	{
	}

	public ModifierGetDevelopmentCardVenture getInstance()
	{
		return ModifierGetDevelopmentCardVenture.INSTANCE;
	}
}
