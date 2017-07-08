package it.polimi.ingsw.lim.common.game.utils;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>This class represents an amount of Development Cards. It is used to
 * represent a condition or a reward.
 */
public class DevelopmentCardAmount implements Serializable
{
	private final CardType cardType;
	private final int amount;

	public DevelopmentCardAmount(CardType cardType, int amount)
	{
		this.cardType = cardType;
		this.amount = amount;
	}

	static String getDevelopmentCardsInformation(List<DevelopmentCardAmount> developmentCardAmounts, boolean indented)
	{
		StringBuilder stringBuilder = new StringBuilder();
		boolean firstLine = true;
		for (DevelopmentCardAmount developmentCardAmount : developmentCardAmounts) {
			if (!firstLine) {
				stringBuilder.append('\n');
			} else {
				firstLine = false;
			}
			if (indented) {
				stringBuilder.append("    ");
			}
			stringBuilder.append(developmentCardAmount.getInformation());
		}
		return stringBuilder.toString();
	}

	public CardType getCardType()
	{
		return this.cardType;
	}

	public int getAmount()
	{
		return this.amount;
	}

	private String getInformation()
	{
		return "- " + CommonUtils.getCardTypesNames().get(this.cardType) + ": " + this.amount;
	}
}
