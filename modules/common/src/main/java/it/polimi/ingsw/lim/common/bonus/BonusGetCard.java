package it.polimi.ingsw.lim.common.bonus;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.events.EventGetCard;
import it.polimi.ingsw.lim.common.game.DiscountChoice;

public class BonusGetCard extends Bonus<EventGetCard>
{
	private final int value;
	private final CardType cardType;
	private final DiscountChoice[] discoutChoices;

	public BonusGetCard(int value, CardType cardType, DiscountChoice[] discountChoices)
	{
		super(EventGetCard.class);
		this.value = value;
		this.cardType = cardType;
		this.discoutChoices = discountChoices;
	}

	@Override
	public EventGetCard apply(EventGetCard event)
	{
		return event;
	}

	public int getValue()
	{
		return this.value;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public DiscountChoice[] getDiscountChoices()
	{
		return this.discoutChoices;
	}
}
