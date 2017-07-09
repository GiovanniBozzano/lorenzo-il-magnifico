package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.server.game.events.EventPickDevelopmentCard;

public class ModifierPickDevelopmentCardValue extends Modifier<EventPickDevelopmentCard>
{
	private final CardType cardType;
	private final int value;

	public ModifierPickDevelopmentCardValue(String description, CardType cardType, int value)
	{
		super(EventPickDevelopmentCard.class, description);
		this.cardType = cardType;
		this.value = value;
	}

	@Override
	public void apply(EventPickDevelopmentCard event)
	{
		if (event.getCardType() == this.cardType) {
			event.setActionValue(event.getActionValue() + this.value);
		}
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventPickDevelopmentCard.class);
	}
}
