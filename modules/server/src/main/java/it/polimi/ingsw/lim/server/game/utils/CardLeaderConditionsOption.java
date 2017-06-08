package it.polimi.ingsw.lim.server.game.utils;

import java.util.List;

public class CardLeaderConditionsOption
{
	private final List<CardAmount> cardAmounts;
	private final List<ResourceAmount> resourceAmounts;

	public CardLeaderConditionsOption(List<CardAmount> cardAmounts, List<ResourceAmount> resourceAmounts)
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
}
