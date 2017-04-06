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
		int firstPeriodIndex = 0;
		int secondPeriodIndex = 0;
		int thirdPeriodIndex = 0;
		for (CardBlue cardBlue : CardBlue.values()) {
			switch (cardBlue.getPeriod()) {
				case FIRST:
					Period.FIRST.getBlueCards()[firstPeriodIndex] = cardBlue;
					firstPeriodIndex++;
					break;
				case SECOND:
					Period.SECOND.getBlueCards()[secondPeriodIndex] = cardBlue;
					secondPeriodIndex++;
					break;
				default:
					Period.THIRD.getBlueCards()[thirdPeriodIndex] = cardBlue;
					thirdPeriodIndex++;
			}
		}
		firstPeriodIndex = 0;
		secondPeriodIndex = 0;
		thirdPeriodIndex = 0;
		for (CardGreen cardGreen : CardGreen.values()) {
			switch (cardGreen.getPeriod()) {
				case FIRST:
					Period.FIRST.getGreenCards()[firstPeriodIndex] = cardGreen;
					firstPeriodIndex++;
					break;
				case SECOND:
					Period.SECOND.getGreenCards()[secondPeriodIndex] = cardGreen;
					secondPeriodIndex++;
					break;
				default:
					Period.THIRD.getGreenCards()[thirdPeriodIndex] = cardGreen;
					thirdPeriodIndex++;
			}
		}
		firstPeriodIndex = 0;
		secondPeriodIndex = 0;
		thirdPeriodIndex = 0;
		for (CardPurple cardPurple : CardPurple.values()) {
			switch (cardPurple.getPeriod()) {
				case FIRST:
					Period.FIRST.getPurpleCards()[firstPeriodIndex] = cardPurple;
					firstPeriodIndex++;
					break;
				case SECOND:
					Period.SECOND.getPurpleCards()[secondPeriodIndex] = cardPurple;
					secondPeriodIndex++;
					break;
				default:
					Period.THIRD.getPurpleCards()[thirdPeriodIndex] = cardPurple;
					thirdPeriodIndex++;
			}
		}
		firstPeriodIndex = 0;
		secondPeriodIndex = 0;
		thirdPeriodIndex = 0;
		for (CardYellow cardYellow : CardYellow.values()) {
			switch (cardYellow.getPeriod()) {
				case FIRST:
					Period.FIRST.getYellowCards()[firstPeriodIndex] = cardYellow;
					firstPeriodIndex++;
					break;
				case SECOND:
					Period.SECOND.getYellowCards()[secondPeriodIndex] = cardYellow;
					secondPeriodIndex++;
					break;
				default:
					Period.THIRD.getYellowCards()[thirdPeriodIndex] = cardYellow;
					thirdPeriodIndex++;
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
