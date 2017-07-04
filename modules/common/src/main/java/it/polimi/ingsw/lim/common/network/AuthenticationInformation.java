package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformation;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformation;
import it.polimi.ingsw.lim.common.game.cards.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationInformation implements Serializable
{
	private Map<Integer, DevelopmentCardBuildingInformation> developmentCardsBuildingInformation;
	private Map<Integer, DevelopmentCardCharacterInformation> developmentCardsCharacterInformation;
	private Map<Integer, DevelopmentCardTerritoryInformation> developmentCardsTerritoryInformation;
	private Map<Integer, DevelopmentCardVentureInformation> developmentCardsVentureInformation;
	private Map<Integer, LeaderCardInformation> leaderCardsInformation;
	private Map<Integer, ExcommunicationTileInformation> excommunicationTilesInformation;
	private Map<Integer, PersonalBonusTileInformation> personalBonusTilesInformation;
	private boolean gameStarted;

	public Map<Integer, DevelopmentCardBuildingInformation> getDevelopmentCardsBuildingInformation()
	{
		return this.developmentCardsBuildingInformation;
	}

	public void setDevelopmentCardsBuildingInformation(Map<Integer, DevelopmentCardBuildingInformation> developmentCardsBuildingInformation)
	{
		this.developmentCardsBuildingInformation = new HashMap<>(developmentCardsBuildingInformation);
	}

	public Map<Integer, DevelopmentCardCharacterInformation> getDevelopmentCardsCharacterInformation()
	{
		return this.developmentCardsCharacterInformation;
	}

	public void setDevelopmentCardsCharacterInformation(Map<Integer, DevelopmentCardCharacterInformation> developmentCardsCharacterInformation)
	{
		this.developmentCardsCharacterInformation = new HashMap<>(developmentCardsCharacterInformation);
	}

	public Map<Integer, DevelopmentCardTerritoryInformation> getDevelopmentCardsTerritoryInformation()
	{
		return this.developmentCardsTerritoryInformation;
	}

	public void setDevelopmentCardsTerritoryInformation(Map<Integer, DevelopmentCardTerritoryInformation> developmentCardsTerritoryInformation)
	{
		this.developmentCardsTerritoryInformation = new HashMap<>(developmentCardsTerritoryInformation);
	}

	public Map<Integer, DevelopmentCardVentureInformation> getDevelopmentCardsVentureInformation()
	{
		return this.developmentCardsVentureInformation;
	}

	public void setDevelopmentCardsVentureInformation(Map<Integer, DevelopmentCardVentureInformation> developmentCardsVentureInformation)
	{
		this.developmentCardsVentureInformation = new HashMap<>(developmentCardsVentureInformation);
	}

	public Map<Integer, LeaderCardInformation> getLeaderCardsInformation()
	{
		return this.leaderCardsInformation;
	}

	public void setLeaderCardsInformation(Map<Integer, LeaderCardInformation> leaderCardsInformation)
	{
		this.leaderCardsInformation = new HashMap<>(leaderCardsInformation);
	}

	public Map<Integer, ExcommunicationTileInformation> getExcommunicationTilesInformation()
	{
		return this.excommunicationTilesInformation;
	}

	public void setExcommunicationTilesInformation(Map<Integer, ExcommunicationTileInformation> excommunicationTilesInformation)
	{
		this.excommunicationTilesInformation = new HashMap<>(excommunicationTilesInformation);
	}

	public Map<Integer, PersonalBonusTileInformation> getPersonalBonusTilesInformation()
	{
		return this.personalBonusTilesInformation;
	}

	public void setPersonalBonusTilesInformation(Map<Integer, PersonalBonusTileInformation> personalBonusTilesInformation)
	{
		this.personalBonusTilesInformation = new HashMap<>(personalBonusTilesInformation);
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
