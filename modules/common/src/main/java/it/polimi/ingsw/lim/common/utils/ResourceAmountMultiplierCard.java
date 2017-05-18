package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;

public class ResourceAmountMultiplierCard extends ResourceAmount
{
	private final CardType cardTypeMultiplier;

	public ResourceAmountMultiplierCard(ResourceType resourceType, int amount, CardType cardTypeMultiplier)
	{
		super(resourceType, amount);
		this.cardTypeMultiplier = cardTypeMultiplier;
	}

	public CardType getCardTypeMultiplier()
	{
		return this.cardTypeMultiplier;
	}
}
