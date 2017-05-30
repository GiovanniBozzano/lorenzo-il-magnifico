package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGetDevelopmentCardVenture extends Modifier<EventGetDevelopmentCard>
{
	private static final ModifierGetDevelopmentCardVenture INSTANCE = new ModifierGetDevelopmentCardVenture();

	private ModifierGetDevelopmentCardVenture()
	{
		super(EventGetDevelopmentCard.class);
	}

	@Override
	public void apply(EventGetDevelopmentCard event)
	{
	}

	public ModifierGetDevelopmentCardVenture getInstance()
	{
		return ModifierGetDevelopmentCardVenture.INSTANCE;
	}
}
