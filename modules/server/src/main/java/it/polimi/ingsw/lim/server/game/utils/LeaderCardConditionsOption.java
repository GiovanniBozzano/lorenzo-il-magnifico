package it.polimi.ingsw.lim.server.game.utils;

import it.polimi.ingsw.lim.common.game.ResourceAmount;
import it.polimi.ingsw.lim.server.game.cards.LeaderCard;

import java.util.List;

public class LeaderCardConditionsOption
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
