package it.polimi.ingsw.lim.common.network;

import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.game.cards.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationInformations implements Serializable
{
	private final Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuildingInformations;
	private final Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacterInformations;
	private final Map<Integer, DevelopmentCardTerritoryInformations> developmentCardsTerritoryInformations;
	private final Map<Integer, DevelopmentCardVentureInformations> developmentCardsVentureInformations;
	private final Map<Integer, LeaderCardInformations> leaderCardsInformations;
	private final Map<Integer, ExcommunicationTileInformations> excommunicationTilesInformations;
	private final Map<Integer, CouncilPalaceRewardInformations> councilPalaceRewardsInformations;
	private final Map<Integer, PersonalBonusTileInformations> personalBonusTilesInformations;
	private final RoomInformations roomInformations;

	public AuthenticationInformations(Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuildingInformations, Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacterInformations, Map<Integer, DevelopmentCardTerritoryInformations> developmentCardsTerritoryInformations, Map<Integer, DevelopmentCardVentureInformations> developmentCardsVentureInformations, Map<Integer, LeaderCardInformations> leaderCardsInformations, Map<Integer, ExcommunicationTileInformations> excommunicationTilesInformations, Map<Integer, CouncilPalaceRewardInformations> councilPalaceRewardsInformations, Map<Integer, PersonalBonusTileInformations> personalBonusTilesInformations, RoomInformations roomInformations)
	{
		this.developmentCardsBuildingInformations = new HashMap<>(developmentCardsBuildingInformations);
		this.developmentCardsCharacterInformations = new HashMap<>(developmentCardsCharacterInformations);
		this.developmentCardsTerritoryInformations = new HashMap<>(developmentCardsTerritoryInformations);
		this.developmentCardsVentureInformations = new HashMap<>(developmentCardsVentureInformations);
		this.leaderCardsInformations = new HashMap<>(leaderCardsInformations);
		this.excommunicationTilesInformations = new HashMap<>(excommunicationTilesInformations);
		this.councilPalaceRewardsInformations = new HashMap<>(councilPalaceRewardsInformations);
		this.personalBonusTilesInformations = new HashMap<>(personalBonusTilesInformations);
		this.roomInformations = roomInformations;
	}

	public Map<Integer, DevelopmentCardBuildingInformations> getDevelopmentCardsBuildingInformations()
	{
		return this.developmentCardsBuildingInformations;
	}

	public Map<Integer, DevelopmentCardCharacterInformations> getDevelopmentCardsCharacterInformations()
	{
		return this.developmentCardsCharacterInformations;
	}

	public Map<Integer, DevelopmentCardTerritoryInformations> getDevelopmentCardsTerritoryInformations()
	{
		return this.developmentCardsTerritoryInformations;
	}

	public Map<Integer, DevelopmentCardVentureInformations> getDevelopmentCardsVentureInformations()
	{
		return this.developmentCardsVentureInformations;
	}

	public Map<Integer, LeaderCardInformations> getLeaderCardsInformations()
	{
		return this.leaderCardsInformations;
	}

	public Map<Integer, ExcommunicationTileInformations> getExcommunicationTilesInformations()
	{
		return this.excommunicationTilesInformations;
	}

	public Map<Integer, CouncilPalaceRewardInformations> getCouncilPalaceRewardsInformations()
	{
		return this.councilPalaceRewardsInformations;
	}

	public Map<Integer, PersonalBonusTileInformations> getPersonalBonusTilesInformations()
	{
		return this.personalBonusTilesInformations;
	}

	public RoomInformations getRoomInformations()
	{
		return this.roomInformations;
	}
}
