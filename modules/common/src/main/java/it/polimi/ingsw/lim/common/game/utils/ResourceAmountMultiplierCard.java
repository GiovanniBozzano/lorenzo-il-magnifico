package it.polimi.ingsw.lim.common.game.utils;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

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
		return resourceAmount instanceof ResourceAmountMultiplierCard && this.getResourceType() == ((ResourceAmountMultiplierCard) resourceAmount).getResourceType() && this.getAmount() == ((ResourceAmountMultiplierCard) resourceAmount).getAmount() && this.cardTypeMultiplier == ((ResourceAmountMultiplierCard) resourceAmount).cardTypeMultiplier;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), this.getResourceType(), this.getAmount(), this.cardTypeMultiplier);
	}

	@Override
	public String getInformations()
	{
		return "- " + CommonUtils.getResourcesTypesNames().get(this.getResourceType()) + ": " + this.getAmount() + " x " + CommonUtils.getCardTypesNames().get(this.cardTypeMultiplier);
	}

	public CardType getCardTypeMultiplier()
	{
		return this.cardTypeMultiplier;
	}
}
