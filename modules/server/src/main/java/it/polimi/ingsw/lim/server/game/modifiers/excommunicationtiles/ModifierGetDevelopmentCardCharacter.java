package it.polimi.ingsw.lim.server.game.modifiers.excommunicationtiles;

import it.polimi.ingsw.lim.server.game.events.EventGetCard;
import it.polimi.ingsw.lim.server.game.modifiers.Modifier;

public class ModifierGetDevelopmentCardCharacter extends Modifier<EventGetCard>
{
	private static final ModifierGetDevelopmentCardCharacter INSTANCE = new ModifierGetDevelopmentCardCharacter();

	private ModifierGetDevelopmentCardCharacter()
	{
		super(EventGetCard.class);
	}

	@Override
	public void apply(EventGetCard event)
	{
	}

	public ModifierGetDevelopmentCardCharacter getInstance()
	{
		return ModifierGetDevelopmentCardCharacter.INSTANCE;
	}
}
