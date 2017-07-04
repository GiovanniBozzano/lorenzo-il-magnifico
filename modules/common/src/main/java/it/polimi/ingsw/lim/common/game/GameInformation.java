package it.polimi.ingsw.lim.common.game;

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
	private final Map<Row, Integer> developmentCardsBuilding;
	private final Map<Row, Integer> developmentCardsCharacter;
	private final Map<Row, Integer> developmentCardsTerritory;
	private final Map<Row, Integer> developmentCardsVenture;
	private final Map<FamilyMemberType, Integer> dices;
	private final Map<Integer, Integer> turnOrder;
	private final Map<Integer, Integer> councilPalaceOrder;
	private final Map<Period, List<Integer>> excommunicatedPlayers;

	public GameInformation(Map<Row, Integer> developmentCardsBuilding, Map<Row, Integer> developmentCardsCharacter, Map<Row, Integer> developmentCardsTerritory, Map<Row, Integer> developmentCardsVenture, Map<FamilyMemberType, Integer> dices, Map<Integer, Integer> turnOrder, Map<Integer, Integer> councilPalaceOrder, Map<Period, List<Integer>> excommunicatedPlayers)
	{
		this.developmentCardsBuilding = new EnumMap<>(developmentCardsBuilding);
		this.developmentCardsCharacter = new EnumMap<>(developmentCardsCharacter);
		this.developmentCardsTerritory = new EnumMap<>(developmentCardsTerritory);
		this.developmentCardsVenture = new EnumMap<>(developmentCardsVenture);
		this.dices = new EnumMap<>(dices);
		this.turnOrder = new HashMap<>(turnOrder);
		this.councilPalaceOrder = new HashMap<>(councilPalaceOrder);
		this.excommunicatedPlayers = new EnumMap<>(excommunicatedPlayers);
	}

	public Map<Row, Integer> getDevelopmentCardsBuilding()
	{
		return this.developmentCardsBuilding;
	}

	public Map<Row, Integer> getDevelopmentCardsCharacter()
	{
		return this.developmentCardsCharacter;
	}

	public Map<Row, Integer> getDevelopmentCardsTerritory()
	{
		return this.developmentCardsTerritory;
	}

	public Map<Row, Integer> getDevelopmentCardsVenture()
	{
		return this.developmentCardsVenture;
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
