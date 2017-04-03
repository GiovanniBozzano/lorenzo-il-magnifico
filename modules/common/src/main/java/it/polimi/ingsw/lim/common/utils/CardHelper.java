package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.EnumCard;

public class CardHelper
{
	private final Period firstPeriod = new Period();
	private final Period secondPeriod = new Period();
	private final Period thirdPeriod = new Period();
	private final EnumCard[] blueOrder = new EnumCard[4];
	private final EnumCard[] greenOrder = new EnumCard[4];
	private final EnumCard[] purpleOrder = new EnumCard[4];
	private final EnumCard[] yellowOrder = new EnumCard[4];

	public CardHelper()
	{
		this.intializeCards();
	}

	private void intializeCards()
	{
		for (EnumCard card : EnumCard.values()) {
			switch (card.getPeriod()) {
				case FIRST:
					initializeInFirstPeriod(card);
					break;
				case SECOND:
					initializeInSecondPeriod(card);
					break;
				default:
					initializeInThirdPeriod(card);
			}
		}
	}

	private void initializeInFirstPeriod(EnumCard card)
	{
		switch (card.getType()) {
			case BLUE:
				firstPeriod.getBlueCards().put(card, true);
				break;
			case GREEN:
				firstPeriod.getGreenCards().put(card, true);
				break;
			case PURPLE:
				firstPeriod.getPurpleCards().put(card, true);
				break;
			default:
				firstPeriod.getYellowCards().put(card, true);
		}
	}

	private void initializeInSecondPeriod(EnumCard card)
	{
		switch (card.getType()) {
			case BLUE:
				secondPeriod.getBlueCards().put(card, true);
				break;
			case GREEN:
				secondPeriod.getGreenCards().put(card, true);
				break;
			case PURPLE:
				secondPeriod.getPurpleCards().put(card, true);
				break;
			default:
				secondPeriod.getYellowCards().put(card, true);
		}
	}

	private void initializeInThirdPeriod(EnumCard card)
	{
		switch (card.getType()) {
			case BLUE:
				thirdPeriod.getBlueCards().put(card, true);
				break;
			case GREEN:
				thirdPeriod.getGreenCards().put(card, true);
				break;
			case PURPLE:
				thirdPeriod.getPurpleCards().put(card, true);
				break;
			default:
				thirdPeriod.getYellowCards().put(card, true);
		}
	}

	Period getFirstPeriod()
	{
		return firstPeriod;
	}

	Period getSecondPeriod()
	{
		return secondPeriod;
	}

	Period getThirdPeriod()
	{
		return thirdPeriod;
	}

	public EnumCard[] getBlueOrder()
	{
		return blueOrder;
	}

	public EnumCard[] getGreenOrder()
	{
		return greenOrder;
	}

	public EnumCard[] getPurpleOrder()
	{
		return purpleOrder;
	}

	public EnumCard[] getYellowOrder()
	{
		return yellowOrder;
	}
}
