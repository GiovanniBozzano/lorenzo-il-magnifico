package it.polimi.ingsw.lim.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum Period
{
	FIRST,
	SECOND,
	THIRD;
	private final Map<CardBlue, Boolean> blueCards = new HashMap<>();
	private final Map<CardGreen, Boolean> greenCards = new HashMap<>();
	private final Map<CardPurple, Boolean> purpleCards = new HashMap<>();
	private final Map<CardYellow, Boolean> yellowCards = new HashMap<>();

	public Map<CardBlue, Boolean> getBlueCards()
	{
		return blueCards;
	}

	public Map<CardGreen, Boolean> getGreenCards()
	{
		return greenCards;
	}

	public Map<CardPurple, Boolean> getPurpleCards()
	{
		return purpleCards;
	}

	public Map<CardYellow, Boolean> getYellowCards()
	{
		return yellowCards;
	}
}
