package it.polimi.ingsw.lim.common.game.utils;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.List;

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

	private String getInformations()
	{
		return "- " + CommonUtils.getCardTypesNames().get(this.cardType) + ": " + this.amount;
	}

	public static String getCardsInformations(List<CardAmount> cardAmounts, boolean indented)
	{
		StringBuilder stringBuilder = new StringBuilder();
		boolean firstLine = true;
		for (CardAmount cardAmount : cardAmounts) {
			if (!firstLine) {
				stringBuilder.append('\n');
			} else {
				firstLine = false;
			}
			if (indented) {
				stringBuilder.append("    ");
			}
			stringBuilder.append(cardAmount.getInformations());
		}
		return stringBuilder.toString();
	}
}
