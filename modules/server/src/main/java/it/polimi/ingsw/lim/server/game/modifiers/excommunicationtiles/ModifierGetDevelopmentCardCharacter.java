package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGetDevelopmentCardCharacter extends Modifier<EventGetDevelopmentCard>
{
	private static final ModifierGetDevelopmentCardCharacter INSTANCE = new ModifierGetDevelopmentCardCharacter();

	private ModifierGetDevelopmentCardCharacter()
	{
		super(EventGetDevelopmentCard.class);
	}

	@Override
	public void apply(EventGetDevelopmentCard event)
	{
	}

	public ModifierGetDevelopmentCardCharacter getInstance()
	{
		return ModifierGetDevelopmentCardCharacter.INSTANCE;
	}
}
