package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.game.CouncilPalaceRewardInformations;
import it.polimi.ingsw.lim.common.game.RoomInformations;
import it.polimi.ingsw.lim.common.game.board.ExcommunicationTileInformations;
import it.polimi.ingsw.lim.common.game.board.PersonalBonusTileInformations;
import it.polimi.ingsw.lim.common.game.cards.*;
import it.polimi.ingsw.lim.common.network.socket.AuthenticationInformationsSocket;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.*;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.board.BoardHandler;
import it.polimi.ingsw.lim.server.game.board.ExcommunicationTile;
import it.polimi.ingsw.lim.server.game.board.PersonalBonusTile;
import it.polimi.ingsw.lim.server.game.cards.*;
import it.polimi.ingsw.lim.server.game.utils.CouncilPalaceReward;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private static final Map<PacketType, IPacketHandler> PACKET_HANDLERS = new EnumMap<>(PacketType.class);

	static {
		PacketListener.PACKET_HANDLERS.put(PacketType.HEARTBEAT, (connectionSocket, packet) -> {
			// This method is empty because it is only called to check the connection.
		});
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_TIMER_REQUEST, (connectionSocket, packet) -> connectionSocket.handleRoomTimerRequest());
		PacketListener.PACKET_HANDLERS.put(PacketType.CHAT_MESSAGE, (connectionSocket, packet) -> connectionSocket.handleChatMessage(((PacketChatMessage) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_PERSONAL_BONUS_TILE_PLAYER_CHOICE, (connectionSocket, packet) -> connectionSocket.handleGamePersonalBonusTilePlayerChoice(((PacketGamePersonalBonusTilePlayerChoice) packet).getPersonalBonusTileIndex()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_LEADER_CARD_PLAYER_CHOICE, (connectionSocket, packet) -> connectionSocket.handleGameLeaderCardPlayerChoice(((PacketGameLeaderCardPlayerChoice) packet).getLeaderCardIndex()));
	}

	private final ConnectionSocket connectionSocket;
	private volatile boolean keepGoing = true;

	PacketListener(ConnectionSocket connectionSocket)
	{
		this.connectionSocket = connectionSocket;
	}

	@Override
	public void run()
	{
		if (!this.waitAuthentication()) {
			return;
		}
		while (this.keepGoing) {
			Packet packet;
			try {
				packet = (Packet) this.connectionSocket.getIn().readObject();
			} catch (ClassNotFoundException | IOException exception) {
				Server.getDebugger().log(Level.INFO, "Socket Client" + (this.connectionSocket.getUsername() != null ? " " + this.connectionSocket.getUsername() : "") + " disconnected.", exception);
				if (!this.keepGoing) {
					return;
				}
				this.connectionSocket.disconnect(false, null);
				return;
			}
			if (packet == null) {
				this.connectionSocket.disconnect(false, null);
				return;
			}
			PacketListener.PACKET_HANDLERS.get(packet.getPacketType()).execute(this.connectionSocket, packet);
		}
	}

	private boolean waitAuthentication()
	{
		while (this.connectionSocket.getUsername() == null) {
			Packet packet;
			try {
				packet = (Packet) this.connectionSocket.getIn().readObject();
			} catch (ClassNotFoundException | IOException exception) {
				Server.getDebugger().log(Level.INFO, "Socket Client disconnected.", exception);
				if (!this.keepGoing) {
					return false;
				}
				this.connectionSocket.disconnect(false, null);
				return false;
			}
			if (packet.getPacketType() != PacketType.LOGIN && packet.getPacketType() != PacketType.REGISTRATION) {
				continue;
			}
			String trimmedUsername = ((PacketAuthentication) packet).getUsername().replaceAll(CommonUtils.REGEX_REMOVE_TRAILING_SPACES, "");
			try {
				if (packet.getPacketType() == PacketType.LOGIN) {
					Utils.checkLogin(((PacketLogin) packet).getVersion(), trimmedUsername, ((PacketLogin) packet).getPassword());
					Utils.displayToLog("Socket Player logged in as: " + trimmedUsername);
				} else if (packet.getPacketType() == PacketType.REGISTRATION) {
					Utils.checkRegistration(((PacketRegistration) packet).getVersion(), trimmedUsername, ((PacketRegistration) packet).getPassword());
					Utils.displayToLog("Socket Player registerd as: " + trimmedUsername);
				}
			} catch (AuthenticationFailedException exception) {
				Server.getDebugger().log(Level.INFO, "Socket Client failed authentication.", exception);
				this.connectionSocket.sendAuthenticationFailure(exception.getLocalizedMessage());
				return false;
			}
			this.connectionSocket.setUsername(trimmedUsername);
			Map<Integer, DevelopmentCardBuildingInformations> developmentCardsBuildingsInformations = new HashMap<>();
			Map<Integer, DevelopmentCardCharacterInformations> developmentCardsCharacterInformations = new HashMap<>();
			Map<Integer, DevelopmentCardTerritoryInformations> developmentsCardTerritoryInformations = new HashMap<>();
			Map<Integer, DevelopmentCardVentureInformations> developmentCardsVentureInformations = new HashMap<>();
			for (Period period : Period.values()) {
				for (DevelopmentCardBuilding developmentCardBuilding : CardsHandler.DEVELOPMENT_CARDS_BUILDING.get(period)) {
					developmentCardsBuildingsInformations.put(developmentCardBuilding.getIndex(), developmentCardBuilding.getInformations());
				}
				for (DevelopmentCardCharacter developmentCardCharacter : CardsHandler.DEVELOPMENT_CARDS_CHARACTER.get(period)) {
					developmentCardsCharacterInformations.put(developmentCardCharacter.getIndex(), developmentCardCharacter.getInformations());
				}
				for (DevelopmentCardTerritory developmentCardTerritory : CardsHandler.DEVELOPMENT_CARDS_TERRITORY.get(period)) {
					developmentsCardTerritoryInformations.put(developmentCardTerritory.getIndex(), developmentCardTerritory.getInformations());
				}
				for (DevelopmentCardVenture developmentCardVenture : CardsHandler.DEVELOPMENT_CARDS_VENTURE.get(period)) {
					developmentCardsVentureInformations.put(developmentCardVenture.getIndex(), developmentCardVenture.getInformations());
				}
			}
			Map<Integer, LeaderCardInformations> leaderCardsInformations = new HashMap<>();
			for (LeaderCard leaderCard : CardsHandler.getLeaderCards()) {
				leaderCardsInformations.put(leaderCard.getIndex(), leaderCard.getInformations());
			}
			Map<Integer, ExcommunicationTileInformations> excommunicationTilesInformations = new HashMap<>();
			for (ExcommunicationTile excommunicationTile : ExcommunicationTile.values()) {
				excommunicationTilesInformations.put(excommunicationTile.getIndex(), new ExcommunicationTileInformations(excommunicationTile.getTexturePath(), excommunicationTile.getModifier().getDescription()));
			}
			Map<Integer, CouncilPalaceRewardInformations> councilPalaceRewardsInformations = new HashMap<>();
			for (CouncilPalaceReward councilPalaceReward : BoardHandler.getCouncilPrivilegeRewards()) {
				councilPalaceRewardsInformations.put(councilPalaceReward.getIndex(), councilPalaceReward.getInformations());
			}
			Map<Integer, PersonalBonusTileInformations> personalBonusTilesInformations = new HashMap<>();
			for (PersonalBonusTile personalBonusTile : PersonalBonusTile.values()) {
				personalBonusTilesInformations.put(personalBonusTile.getIndex(), new PersonalBonusTileInformations(personalBonusTile.getTexturePath(), personalBonusTile.getProductionActivationCost(), personalBonusTile.getProductionInstantResources(), personalBonusTile.getHarvestActivationCost(), personalBonusTile.getHarvestInstantResources()));
			}
			Room playerRoom;
			if ((playerRoom = Room.getPlayerRoom(trimmedUsername)) == null) {
				Room targetRoom = null;
				for (Room room : Server.getInstance().getRooms()) {
					if (room.getGameHandler() == null && room.getRoomType() == ((PacketAuthentication) packet).getRoomType() && room.getPlayers().size() < ((PacketAuthentication) packet).getRoomType().getPlayersNumber()) {
						targetRoom = room;
						break;
					}
				}
				if (targetRoom == null) {
					targetRoom = new Room(((PacketAuthentication) packet).getRoomType());
					Server.getInstance().getRooms().add(targetRoom);
				}
				targetRoom.addPlayer(this.connectionSocket);
				List<String> playerUsernames = new ArrayList<>();
				for (Connection player : targetRoom.getPlayers()) {
					player.sendRoomEntryOther(trimmedUsername);
					playerUsernames.add(player.getUsername());
				}
				this.connectionSocket.sendAuthenticationConfirmation(new AuthenticationInformationsSocket(developmentCardsBuildingsInformations, developmentCardsCharacterInformations, developmentsCardTerritoryInformations, developmentCardsVentureInformations, leaderCardsInformations, excommunicationTilesInformations, councilPalaceRewardsInformations, personalBonusTilesInformations, new RoomInformations(targetRoom.getRoomType(), playerUsernames), trimmedUsername));
			} else {
				for (Connection player : playerRoom.getPlayers()) {
					if (player.getUsername().equals(trimmedUsername)) {
						player.getPlayerHandler().setOnline(true);
						this.connectionSocket.setPlayerHandler(player.getPlayerHandler());
						playerRoom.getPlayers().set(playerRoom.getPlayers().indexOf(player), this.connectionSocket);
						playerRoom.getPlayers().remove(player);
						break;
					}
				}
				// TODO aggiorno il giocatore
				List<String> playerUsernames = new ArrayList<>();
				for (Connection player : playerRoom.getPlayers()) {
					playerUsernames.add(player.getUsername());
				}
				this.connectionSocket.sendAuthenticationConfirmation(new AuthenticationInformationsSocket(developmentCardsBuildingsInformations, developmentCardsCharacterInformations, developmentsCardTerritoryInformations, developmentCardsVentureInformations, leaderCardsInformations, excommunicationTilesInformations, councilPalaceRewardsInformations, personalBonusTilesInformations, new RoomInformations(playerRoom.getRoomType(), playerUsernames), trimmedUsername));
			}
		}
		return true;
	}

	synchronized void end()
	{
		this.keepGoing = false;
	}
}
