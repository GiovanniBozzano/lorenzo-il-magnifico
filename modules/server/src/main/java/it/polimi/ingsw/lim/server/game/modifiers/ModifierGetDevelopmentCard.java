package it.polimi.ingsw.lim.server.game.modifiers;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.server.game.events.EventGetDevelopmentCard;

import java.util.List;

/**
 * <p>Whenever you perform an action to take a {@code cardType} card (through a
 * Family Member or as an efect of another card), increase the value of the
 * action by {@code value}. In addition, the cost of the card you take is
 * reduced by {@code discountChoices}.
 */
public class ModifierGetDevelopmentCard extends Modifier<EventGetDevelopmentCard>
{
	private final int value;
	private final CardType cardType;
	private final List<List<ResourceAmount>> discoutChoices;

	public ModifierGetDevelopmentCard(int value, CardType cardType, List<List<ResourceAmount>> discountChoices)
	{
		super(EventGetDevelopmentCard.class);
		this.value = value;
		this.cardType = cardType;
		this.discoutChoices = discountChoices;
	}

	@Override
	public void apply(EventGetDevelopmentCard event)
	{
	}

	public List<List<ResourceAmount>> getDiscountChoices()
	{
		return this.discoutChoices;
	}
}
