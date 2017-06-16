package it.polimi.ingsw.lim.common.network.socket;

import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.network.AuthenticationInformations;

import java.util.Map;

public class AuthenticationInformationsSocket extends AuthenticationInformations
{
	private final String username;

	public AuthenticationInformationsSocket(Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuildingInformations, Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacterInformations, Map<Integer, DevelopmentCardTerritoryInformations> developmentCardsTerritoryInformations, Map<Integer, DevelopmentCardVentureInformations> developmentCardsVentureInformations, Map<Integer, LeaderCardInformations> leaderCardsInformations, Map<Integer, ExcommunicationTileInformations> excommunicationTilesInformations, Map<Integer, CouncilPalaceRewardInformations> councilPalaceRewardsInformations, Map<Integer, PersonalBonusTileInformations> personalBonusTilesInformations, RoomInformations roomInformations, String username)
	{
		super(developmentCardsBuildingInformations, developmentCardsCharacterInformations, developmentCardsTerritoryInformations, developmentCardsVentureInformations, leaderCardsInformations, excommunicationTilesInformations, councilPalaceRewardsInformations, personalBonusTilesInformations, roomInformations);
		this.username = username;
	}

	public String getUsername()
	{
		return this.username;
	}
}
