package it.polimi.ingsw.lim.common.game;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.enums.Row;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInformation implements Serializable
{
	private final Map<CardType, Map<Row, Integer>> developmentCards;
	private final Map<FamilyMemberType, Integer> dices;
	private final Map<Integer, Integer> turnOrder;
	private final Map<Integer, Integer> councilPalaceOrder;
	private final Map<Period, List<Integer>> excommunicatedPlayers;

	public GameInformation(Map<CardType, Map<Row, Integer>> developmentCards, Map<FamilyMemberType, Integer> dices, Map<Integer, Integer> turnOrder, Map<Integer, Integer> councilPalaceOrder, Map<Period, List<Integer>> excommunicatedPlayers)
	{
		this.developmentCards = new EnumMap<>(developmentCards);
		this.dices = new EnumMap<>(dices);
		this.turnOrder = new HashMap<>(turnOrder);
		this.councilPalaceOrder = new HashMap<>(councilPalaceOrder);
		this.excommunicatedPlayers = new EnumMap<>(excommunicatedPlayers);
	}

	public Map<CardType, Map<Row, Integer>> getDevelopmentCards()
	{
		return this.developmentCards;
	}

	public Map<FamilyMemberType, Integer> getDices()
	{
		return this.dices;
	}

	public Map<Integer, Integer> getTurnOrder()
	{
		return this.turnOrder;
	}

	public Map<Integer, Integer> getCouncilPalaceOrder()
	{
		return this.councilPalaceOrder;
	}

	public Map<Period, List<Integer>> getExcommunicatedPlayers()
	{
		return this.excommunicatedPlayers;
	}
}
