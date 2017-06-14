package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformations;
import it.polimi.ingsw.lim.common.game.cards.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationInformations implements Serializable
{
	private final List<DevelopmentCardInformations> developmentCardsInformations;
	private final List<LeaderCardInformations> leaderCardsInformations;
	private final List<ExcommunicationTileInformations> excommunicationTilesInformations;
	private final List<CouncilPalaceRewardInformations> councilPalaceRewardInformations;
	@SuppressWarnings("squid:S1948") private final IClientSession clientSession;
	private final RoomInformations roomInformations;

	public AuthenticationInformations(List<DevelopmentCardInformations> developmentCardsInformations, List<LeaderCardInformations> leaderCardsInformations, List<ExcommunicationTileInformations> excommunicationTilesInformations, List<CouncilPalaceRewardInformations> councilPalaceRewardInformations, IClientSession clientSession, RoomInformations roomInformations)
	{
		this.developmentCardsInformations = new ArrayList<>(developmentCardsInformations);
		this.leaderCardsInformations = new ArrayList<>(leaderCardsInformations);
		this.excommunicationTilesInformations = new ArrayList<>(excommunicationTilesInformations);
		this.councilPalaceRewardInformations = new ArrayList<>(councilPalaceRewardInformations);
		this.clientSession = clientSession;
		this.roomInformations = roomInformations;
	}

	public List<DevelopmentCardInformations> getDevelopmentCardsInformations()
	{
		return this.developmentCardsInformations;
	}

	public List<LeaderCardInformations> getLeaderCardsInformations()
	{
		return this.leaderCardsInformations;
	}

	public List<ExcommunicationTileInformations> getExcommunicationTilesInformations()
	{
		return this.excommunicationTilesInformations;
	}

	public List<CouncilPalaceRewardInformations> getCouncilPalaceRewardInformations()
	{
		return this.councilPalaceRewardInformations;
	}

	public IClientSession getClientSession()
	{
		return this.clientSession;
	}

	public RoomInformations getRoomInformations()
	{
		return this.roomInformations;
	}
}
