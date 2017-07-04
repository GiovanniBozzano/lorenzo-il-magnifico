package it.polimi.ingsw.lim.common.game.utils;

import java.io.Serializable;
import java.util.List;

public class LeaderCardConditionsOption implements Serializable
{
	private final List<CardAmount> cardAmounts;
	private final List<ResourceAmount> resourceAmounts;

	public LeaderCardConditionsOption(List<CardAmount> cardAmounts, List<ResourceAmount> resourceAmounts)
	{
		this.cardAmounts = cardAmounts;
		this.resourceAmounts = resourceAmounts;
	}

	public List<CardAmount> getCardAmounts()
	{
		return this.cardAmounts;
	}

	public List<ResourceAmount> getResourceAmounts()
	{
		return this.resourceAmounts;
	}

	public String getInformations()
	{
		StringBuilder stringBuilder = new StringBuilder();
		if (!this.resourceAmounts.isEmpty()) {
			stringBuilder.append("\nRequired resources:\n");
			stringBuilder.append(ResourceAmount.getResourcesInformations(this.resourceAmounts, true));
		}
		if (!this.cardAmounts.isEmpty()) {
			stringBuilder.append("\nRequired cards:\n");
			stringBuilder.append(CardAmount.getCardsInformations(this.cardAmounts, true));
		}
		return stringBuilder.toString();
	}
}
