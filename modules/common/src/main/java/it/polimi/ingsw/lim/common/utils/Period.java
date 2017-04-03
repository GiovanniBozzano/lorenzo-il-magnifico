package it.polimi.ingsw.lim.common.utils;

import it.polimi.ingsw.lim.common.enums.EnumCard;

import java.util.HashMap;
import java.util.Map;

public class Period
{
	private final Map<EnumCard, Boolean> blueCards = new HashMap<>();
	private final Map<EnumCard, Boolean> greenCards = new HashMap<>();
	private final Map<EnumCard, Boolean> purpleCards = new HashMap<>();
	private final Map<EnumCard, Boolean> yellowCards = new HashMap<>();

	public Map<EnumCard, Boolean> getBlueCards()
	{
		return blueCards;
	}

	public Map<EnumCard, Boolean> getGreenCards()
	{
		return greenCards;
	}

	public Map<EnumCard, Boolean> getPurpleCards()
	{
		return purpleCards;
	}

	public Map<EnumCard, Boolean> getYellowCards()
	{
		return yellowCards;
	}
}
