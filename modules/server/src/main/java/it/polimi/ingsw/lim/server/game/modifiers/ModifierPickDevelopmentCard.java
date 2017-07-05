package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.events.EventPickDevelopmentCard;

import java.util.List;

/**
 * <p>This class represents a modifier with the following effect:
 *
 * <p>Whenever you perform an action to take a {@code cardType} card (through a
 * Family Member or as an effect of another card), increase the value of the
 * action by {@code value}. In addition, the cost of the card you take is
 * reduced by {@code discountChoices}.
 */
public class ModifierPickDevelopmentCard extends Modifier<EventPickDevelopmentCard>
{
	private final int value;
	private final CardType cardType;
	private final List<List<ResourceAmount>> discountChoices;

	public ModifierPickDevelopmentCard(String description, int value, CardType cardType, List<List<ResourceAmount>> discountChoices)
	{
		super(EventPickDevelopmentCard.class, description);
		this.value = value;
		this.cardType = cardType;
		this.discountChoices = discountChoices;
	}

	@Override
	public void apply(EventPickDevelopmentCard event)
	{
		event.setActionValue(event.getActionValue() + this.value);
	}

	@Override
	public void setEventClass()
	{
		super.setEventClass(EventPickDevelopmentCard.class);
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public List<List<ResourceAmount>> getDiscountChoices()
	{
		return this.discountChoices;
	}
}
