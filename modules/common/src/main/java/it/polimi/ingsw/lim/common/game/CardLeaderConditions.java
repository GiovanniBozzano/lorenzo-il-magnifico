package it.polimi.ingsw.lim.common.game;

public class CardLeaderConditions
{
	private CardAmount[] cardAmounts;
	private ResourceAmount[] resourceAmounts;

	public CardLeaderConditions(CardAmount[] cardAmounts, ResourceAmount[] resourceAmounts)
	{
		this.cardAmounts = cardAmounts;
		this.resourceAmounts = resourceAmounts;
	}

	public CardAmount[] getCardAmounts()
	{
		return this.cardAmounts;
	}

	public ResourceAmount[] getResourceAmounts()
	{
		return this.resourceAmounts;
	}
}
