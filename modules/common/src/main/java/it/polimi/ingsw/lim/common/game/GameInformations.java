package it.polimi.ingsw.lim.common.game;

import it.polimi.ingsw.lim.common.enums.Row;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class GameInformations implements Serializable
{
	private final Map<Row, Integer> developmentCardsBuilding;
	private final Map<Row, Integer> developmentCardsCharacter;
	private final Map<Row, Integer> developmentCardsTerritory;
	private final Map<Row, Integer> developmentCardsVenture;
	private final Map<Integer, Integer> turnOrder;
	private final Map<Integer, Integer> councilPalaceOrder;

	public GameInformations(Map<Row, Integer> developmentCardsBuilding, Map<Row, Integer> developmentCardsCharacter, Map<Row, Integer> developmentCardsTerritory, Map<Row, Integer> developmentCardsVenture, Map<Integer, Integer> turnOrder, Map<Integer, Integer> councilPalaceOrder)
	{
		this.developmentCardsBuilding = new EnumMap<>(developmentCardsBuilding);
		this.developmentCardsCharacter = new EnumMap<>(developmentCardsCharacter);
		this.developmentCardsTerritory = new EnumMap<>(developmentCardsTerritory);
		this.developmentCardsVenture = new EnumMap<>(developmentCardsVenture);
		this.turnOrder = new HashMap<>(turnOrder);
		this.councilPalaceOrder = new HashMap<>(councilPalaceOrder);
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

	public Map<Integer, Integer> getTurnOrder()
	{
		return this.turnOrder;
	}

	public Map<Integer, Integer> getCouncilPalaceOrder()
	{
		return this.councilPalaceOrder;
	}
}
