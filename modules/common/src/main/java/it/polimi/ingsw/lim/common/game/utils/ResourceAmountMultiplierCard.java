package it.polimi.ingsw.lim.common.game.utils;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;

import java.util.Objects;

public class ResourceAmountMultiplierCard extends ResourceAmount
{
	private final CardType cardTypeMultiplier;

	public ResourceAmountMultiplierCard(ResourceType resourceType, int amount, CardType cardTypeMultiplier)
	{
		super(resourceType, amount);
		this.cardTypeMultiplier = cardTypeMultiplier;
	}

	@Override
	public boolean equals(Object resourceAmount)
	{
		if (!(resourceAmount instanceof ResourceAmountMultiplierCard)) {
			return false;
		}
		return this.getResourceType() == ((ResourceAmountMultiplierCard) resourceAmount).getResourceType() && this.getAmount() == ((ResourceAmountMultiplierCard) resourceAmount).getAmount() && this.cardTypeMultiplier == ((ResourceAmountMultiplierCard) resourceAmount).cardTypeMultiplier;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.getResourceType(), this.getAmount(), this.cardTypeMultiplier);
	}

	public CardType getCardTypeMultiplier()
	{
		return this.cardTypeMultiplier;
	}
}
