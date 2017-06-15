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
}
