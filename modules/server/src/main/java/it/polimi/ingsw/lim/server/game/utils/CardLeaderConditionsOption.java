package it.polimi.ingsw.lim.server.game.utils;

public class CardLeaderConditionsOption
{
	private final CardAmount[] cardAmounts;
	private final ResourceAmount[] resourceAmounts;

	public CardLeaderConditionsOption(CardAmount[] cardAmounts, ResourceAmount[] resourceAmounts)
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
