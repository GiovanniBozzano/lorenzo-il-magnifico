package it.polimi.ingsw.lim.common.enums;

public enum Period
{
	FIRST,
	SECOND,
	THIRD;
	private final CardBlue[] blueCards = new CardBlue[8];
	private final CardGreen[] greenCards = new CardGreen[8];
	private final CardPurple[] purpleCards = new CardPurple[8];
	private final CardYellow[] yellowCards = new CardYellow[8];

	public CardBlue[] getBlueCards()
	{
		return this.blueCards;
	}

	public CardGreen[] getGreenCards()
	{
		return this.greenCards;
	}

	public CardPurple[] getPurpleCards()
	{
		return this.purpleCards;
	}

	public CardYellow[] getYellowCards()
	{
		return this.yellowCards;
	}
}
