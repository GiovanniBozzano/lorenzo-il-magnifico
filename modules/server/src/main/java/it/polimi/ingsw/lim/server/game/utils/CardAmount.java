package it.polimi.ingsw.lim.server.game.utils;

import it.polimi.ingsw.lim.common.enums.CardType;

public class CardAmount
{
	private CardType cardType;
	private int amount;

	public CardAmount(CardType cardType, int amount)
	{
		this.cardType = cardType;
		this.amount = amount;
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public int getAmount()
	{
		return this.amount;
	}
}
