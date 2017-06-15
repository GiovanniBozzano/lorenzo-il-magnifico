package it.polimi.ingsw.lim.common.game.utils;

import it.polimi.ingsw.lim.common.enums.CardType;

import java.io.Serializable;

public class CardAmount implements Serializable
{
	private final CardType cardType;
	private final int amount;

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
