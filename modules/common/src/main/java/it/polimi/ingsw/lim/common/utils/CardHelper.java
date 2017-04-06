package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.*;

public class CardHelper
{
	private final CardBlue[] blueOrder = new CardBlue[4];
	private final CardGreen[] greenOrder = new CardGreen[4];
	private final CardPurple[] purpleOrder = new CardPurple[4];
	private final CardYellow[] yellowOrder = new CardYellow[4];

	public CardHelper()
	{
		this.intializeCards();
	}

	private void intializeCards()
	{
		for (int counter = 0; counter < CardBlue.values().length; counter++) {
			CardBlue cardBlue = CardBlue.values()[counter];
			switch (cardBlue.getPeriod()) {
				case FIRST:
					Period.FIRST.getBlueCards()[counter] = cardBlue;
					break;
				case SECOND:
					Period.SECOND.getBlueCards()[counter] = cardBlue;
					break;
				default:
					Period.THIRD.getBlueCards()[counter] = cardBlue;
			}
		}
		for (int counter = 0; counter < CardGreen.values().length; counter++) {
			CardGreen cardGreen = CardGreen.values()[counter];
			switch (cardGreen.getPeriod()) {
				case FIRST:
					Period.FIRST.getGreenCards()[counter] = cardGreen;
					break;
				case SECOND:
					Period.SECOND.getGreenCards()[counter] = cardGreen;
					break;
				default:
					Period.THIRD.getGreenCards()[counter] = cardGreen;
			}
		}
		for (int counter = 0; counter < CardPurple.values().length; counter++) {
			CardPurple cardPurple = CardPurple.values()[counter];
			switch (cardPurple.getPeriod()) {
				case FIRST:
					Period.FIRST.getPurpleCards()[counter] = cardPurple;
					break;
				case SECOND:
					Period.SECOND.getPurpleCards()[counter] = cardPurple;
					break;
				default:
					Period.THIRD.getPurpleCards()[counter] = cardPurple;
			}
		}
		for (int counter = 0; counter < CardYellow.values().length; counter++) {
			CardYellow cardYellow = CardYellow.values()[counter];
			switch (cardYellow.getPeriod()) {
				case FIRST:
					Period.FIRST.getYellowCards()[counter] = cardYellow;
					break;
				case SECOND:
					Period.SECOND.getYellowCards()[counter] = cardYellow;
					break;
				default:
					Period.THIRD.getYellowCards()[counter] = cardYellow;
			}
		}
	}

	public CardBlue[] getBlueOrder()
	{
		return blueOrder;
	}

	public CardGreen[] getGreenOrder()
	{
		return greenOrder;
	}

	public CardPurple[] getPurpleOrder()
	{
		return purpleOrder;
	}

	public CardYellow[] getYellowOrder()
	{
		return yellowOrder;
	}
}
