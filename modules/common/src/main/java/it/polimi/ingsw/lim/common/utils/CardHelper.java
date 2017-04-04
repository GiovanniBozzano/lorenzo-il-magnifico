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
		for (CardBlue cardBlue : CardBlue.values()) {
			switch (cardBlue.getPeriod()) {
				case FIRST:
					Period.FIRST.getBlueCards().put(cardBlue, true);
					break;
				case SECOND:
					Period.SECOND.getBlueCards().put(cardBlue, true);
					break;
				default:
					Period.THIRD.getBlueCards().put(cardBlue, true);
			}
		}
		for (CardGreen cardGreen : CardGreen.values()) {
			switch (cardGreen.getPeriod()) {
				case FIRST:
					Period.FIRST.getGreenCards().put(cardGreen, true);
					break;
				case SECOND:
					Period.SECOND.getGreenCards().put(cardGreen, true);
					break;
				default:
					Period.THIRD.getGreenCards().put(cardGreen, true);
			}
		}
		for (CardPurple cardPurple : CardPurple.values()) {
			switch (cardPurple.getPeriod()) {
				case FIRST:
					Period.FIRST.getPurpleCards().put(cardPurple, true);
					break;
				case SECOND:
					Period.SECOND.getPurpleCards().put(cardPurple, true);
					break;
				default:
					Period.THIRD.getPurpleCards().put(cardPurple, true);
			}
		}
		for (CardYellow cardYellow : CardYellow.values()) {
			switch (cardYellow.getPeriod()) {
				case FIRST:
					Period.FIRST.getYellowCards().put(cardYellow, true);
					break;
				case SECOND:
					Period.SECOND.getYellowCards().put(cardYellow, true);
					break;
				default:
					Period.THIRD.getYellowCards().put(cardYellow, true);
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
