package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformation;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformation;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformation;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformation;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationInformation implements Serializable
{
	private final Map<CardType, Map<Integer, DevelopmentCardInformation>> developmentCardsInformation = new EnumMap<>(CardType.class);
	private final Map<Integer, LeaderCardInformation> leaderCardsInformation = new HashMap<>();
	private final Map<Integer, ExcommunicationTileInformation> excommunicationTilesInformation = new HashMap<>();
	private final Map<Integer, PersonalBonusTileInformation> personalBonusTilesInformation = new HashMap<>();
	private boolean gameStarted;

	public AuthenticationInformation()
	{
		this.developmentCardsInformation.put(CardType.BUILDING, new HashMap<>());
		this.developmentCardsInformation.put(CardType.CHARACTER, new HashMap<>());
		this.developmentCardsInformation.put(CardType.TERRITORY, new HashMap<>());
		this.developmentCardsInformation.put(CardType.VENTURE, new HashMap<>());
	}

	public void setDevelopmentCardsInformation(CardType cardType, Map<Integer, DevelopmentCardInformation> developmentCardsInformation)
	{
		this.developmentCardsInformation.get(cardType).clear();
		this.developmentCardsInformation.get(cardType).putAll(developmentCardsInformation);
	}

	public Map<CardType, Map<Integer, DevelopmentCardInformation>> getDevelopmentCardsInformation()
	{
		return this.developmentCardsInformation;
	}

	public Map<Integer, LeaderCardInformation> getLeaderCardsInformation()
	{
		return this.leaderCardsInformation;
	}

	public void setLeaderCardsInformation(Map<Integer, LeaderCardInformation> leaderCardsInformation)
	{
		this.leaderCardsInformation.clear();
		this.leaderCardsInformation.putAll(leaderCardsInformation);
	}

	public Map<Integer, ExcommunicationTileInformation> getExcommunicationTilesInformation()
	{
		return this.excommunicationTilesInformation;
	}

	public void setExcommunicationTilesInformation(Map<Integer, ExcommunicationTileInformation> excommunicationTilesInformation)
	{
		this.excommunicationTilesInformation.clear();
		this.excommunicationTilesInformation.putAll(excommunicationTilesInformation);
	}

	public Map<Integer, PersonalBonusTileInformation> getPersonalBonusTilesInformation()
	{
		return this.personalBonusTilesInformation;
	}

	public void setPersonalBonusTilesInformation(Map<Integer, PersonalBonusTileInformation> personalBonusTilesInformation)
	{
		this.personalBonusTilesInformation.clear();
		this.personalBonusTilesInformation.putAll(personalBonusTilesInformation);
	}

	public boolean isGameStarted()
	{
		return this.gameStarted;
	}

	public void setGameStarted(boolean gameStarted)
	{
		this.gameStarted = gameStarted;
	}
}
