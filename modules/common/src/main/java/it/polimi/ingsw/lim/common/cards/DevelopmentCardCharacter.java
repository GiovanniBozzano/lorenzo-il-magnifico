package it.polimi.ingsw.lim.common.cards;

import it.polimi.ingsw.lim.common.game.Reward;

public class DevelopmentCardCharacter extends Card
{
	private final int price;
	private final Reward instantReward;
	private final Object permanentBonus;

	public DevelopmentCardCharacter(String displayName, int price, Reward instantReward, Object permanentBonus)
	{
		super(displayName);
		this.price = price;
		this.instantReward = instantReward;
		this.permanentBonus = permanentBonus;
	}

	public int getPrice()
	{
		return this.price;
	}

	public Reward getInstantReward()
	{
		return this.instantReward;
	}

	public Object getPermanentBonus()
	{
		return this.permanentBonus;
	}
}
