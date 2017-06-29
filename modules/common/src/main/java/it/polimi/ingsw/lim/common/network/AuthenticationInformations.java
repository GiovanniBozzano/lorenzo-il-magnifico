package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.game.cards.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AuthenticationInformations implements Serializable
{
	private Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuildingInformations;
	private Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacterInformations;
	private Map<Integer, DevelopmentCardTerritoryInformations> developmentCardsTerritoryInformations;
	private Map<Integer, DevelopmentCardVentureInformations> developmentCardsVentureInformations;
	private Map<Integer, LeaderCardInformations> leaderCardsInformations;
	private Map<Integer, ExcommunicationTileInformations> excommunicationTilesInformations;
	private Map<Integer, PersonalBonusTileInformations> personalBonusTilesInformations;
	private boolean gameStarted;

	public Map<Integer, DevelopmentCardBuildingInformations> getDevelopmentCardsBuildingInformations()
	{
		return this.developmentCardsBuildingInformations;
	}

	public void setDevelopmentCardsBuildingInformations(Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuildingInformations)
	{
		this.developmentCardsBuildingInformations = new HashMap<>(developmentCardsBuildingInformations);
	}

	public Map<Integer, DevelopmentCardCharacterInformations> getDevelopmentCardsCharacterInformations()
	{
		return this.developmentCardsCharacterInformations;
	}

	public void setDevelopmentCardsCharacterInformations(Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacterInformations)
	{
		this.developmentCardsCharacterInformations = new HashMap<>(developmentCardsCharacterInformations);
	}

	public Map<Integer, DevelopmentCardTerritoryInformations> getDevelopmentCardsTerritoryInformations()
	{
		return this.developmentCardsTerritoryInformations;
	}

	public void setDevelopmentCardsTerritoryInformations(Map<Integer, DevelopmentCardTerritoryInformations> developmentCardsTerritoryInformations)
	{
		this.developmentCardsTerritoryInformations = new HashMap<>(developmentCardsTerritoryInformations);
	}

	public Map<Integer, DevelopmentCardVentureInformations> getDevelopmentCardsVentureInformations()
	{
		return this.developmentCardsVentureInformations;
	}

	public void setDevelopmentCardsVentureInformations(Map<Integer, DevelopmentCardVentureInformations> developmentCardsVentureInformations)
	{
		this.developmentCardsVentureInformations = new HashMap<>(developmentCardsVentureInformations);
	}

	public Map<Integer, LeaderCardInformations> getLeaderCardsInformations()
	{
		return this.leaderCardsInformations;
	}

	public void setLeaderCardsInformations(Map<Integer, LeaderCardInformations> leaderCardsInformations)
	{
		this.leaderCardsInformations = new HashMap<>(leaderCardsInformations);
	}

	public Map<Integer, ExcommunicationTileInformations> getExcommunicationTilesInformations()
	{
		return this.excommunicationTilesInformations;
	}

	public void setExcommunicationTilesInformations(Map<Integer, ExcommunicationTileInformations> excommunicationTilesInformations)
	{
		this.excommunicationTilesInformations = new HashMap<>(excommunicationTilesInformations);
	}

	public Map<Integer, PersonalBonusTileInformations> getPersonalBonusTilesInformations()
	{
		return this.personalBonusTilesInformations;
	}

	public void setPersonalBonusTilesInformations(Map<Integer, PersonalBonusTileInformations> personalBonusTilesInformations)
	{
		this.personalBonusTilesInformations = new HashMap<>(personalBonusTilesInformations);
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
