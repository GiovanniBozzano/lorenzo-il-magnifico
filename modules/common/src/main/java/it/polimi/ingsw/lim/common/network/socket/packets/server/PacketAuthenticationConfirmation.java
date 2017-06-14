package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.game.cards.DevelopmentCardInformations;
import it.polimi.ingsw.lim.common.game.cards.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketAuthenticationConfirmation extends Packet
{
	private final List<DevelopmentCardInformations> developmentCardsInformations;
	private final List<LeaderCardInformations> leaderCardsInformations;
	private final List<ExcommunicationTileInformations> excommunicationTilesInformations;
	private final List<CouncilPalaceRewardInformations> councilPalaceRewardInformations;
	private final String username;
	private final RoomInformations roomInformations;

	public PacketAuthenticationConfirmation(List<DevelopmentCardInformations> developmentCardsInformations, List<LeaderCardInformations> leaderCardsInformations, List<ExcommunicationTileInformations> excommunicationTilesInformations, List<CouncilPalaceRewardInformations> councilPalaceRewardInformations, String username, RoomInformations roomInformations)
	{
		super(PacketType.AUTHENTICATION_CONFIRMATION);
		this.developmentCardsInformations = new ArrayList<>(developmentCardsInformations);
		this.leaderCardsInformations = new ArrayList<>(leaderCardsInformations);
		this.excommunicationTilesInformations = new ArrayList<>(excommunicationTilesInformations);
		this.councilPalaceRewardInformations = new ArrayList<>(councilPalaceRewardInformations);
		this.username = username;
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

	public String getUsername()
	{
		return this.username;
	}

	public RoomInformations getRoomInformations()
	{
		return this.roomInformations;
	}
}
