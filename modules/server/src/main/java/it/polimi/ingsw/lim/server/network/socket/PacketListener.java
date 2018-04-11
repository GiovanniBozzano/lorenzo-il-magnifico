package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.AuthenticationFailedException;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.game.RoomInformation;
import it.polimi.ingsw.lim.common.network.AuthenticationInformation;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationGame;
import it.polimi.ingsw.lim.common.network.socket.AuthenticationInformationLobbySocket;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.client.*;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private static final Map<PacketType, IPacketHandler> PACKET_HANDLERS = new EnumMap<>(PacketType.class);

	static {
		PacketListener.PACKET_HANDLERS.put(PacketType.CHAT_MESSAGE, (connectionSocket, packet) -> connectionSocket.handleChatMessage(((PacketChatMessage) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_ACTION, (connectionSocket, packet) -> {
			try {
				connectionSocket.handleGameAction(((PacketGameAction) packet).getAction());
			} catch (GameActionFailedException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				connectionSocket.sendGameActionFailed(exception.getLocalizedMessage());
			}
		});
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_EXCOMMUNICATION_PLAYER_CHOICE, (connectionSocket, packet) -> {
			try {
				connectionSocket.handleGameExcommunicationPlayerChoice(((PacketGameExcommunicationPlayerChoice) packet).isExcommunicated());
			} catch (GameActionFailedException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				connectionSocket.sendGameActionFailed(exception.getLocalizedMessage());
			}
		});
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_LEADER_CARD_PLAYER_CHOICE, (connectionSocket, packet) -> {
			try {
				connectionSocket.handleGameLeaderCardPlayerChoice(((PacketGameLeaderCardPlayerChoice) packet).getLeaderCardIndex());
			} catch (GameActionFailedException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				connectionSocket.sendGameActionFailed(exception.getLocalizedMessage());
			}
		});
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_PERSONAL_BONUS_TILE_PLAYER_CHOICE, (connectionSocket, packet) -> {
			try {
				connectionSocket.handleGamePersonalBonusTilePlayerChoice(((PacketGamePersonalBonusTilePlayerChoice) packet).getPersonalBonusTileIndex());
			} catch (GameActionFailedException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				connectionSocket.sendGameActionFailed(exception.getLocalizedMessage());
			}
		});
		PacketListener.PACKET_HANDLERS.put(PacketType.GOOD_GAME, (connectionSocket, packet) -> {
			try {
				connectionSocket.handleGoodGame(((PacketGoodGame) packet).getPlayerIndex());
			} catch (GameActionFailedException exception) {
				Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
				connectionSocket.sendGameActionFailed(exception.getLocalizedMessage());
			}
		});
		PacketListener.PACKET_HANDLERS.put(PacketType.HEARTBEAT, (connectionSocket, packet) -> {
			// This method is empty because it is only called to check the connection.
		});
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_TIMER_REQUEST, (connectionSocket, packet) -> connectionSocket.handleRoomTimerRequest());
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
				Server.getDebugger().log(Level.INFO, "Socket Client" + (this.connectionSocket.getUsername() != null ? ' ' + this.connectionSocket.getUsername() : "") + " disconnected.", exception);
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
		Packet packet;
		do {
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
					Server.getInstance().getInterfaceHandler().displayToLog("Socket Player logged in as: " + trimmedUsername);
				} else if (packet.getPacketType() == PacketType.REGISTRATION) {
					Utils.checkRegistration(((PacketRegistration) packet).getVersion(), trimmedUsername, ((PacketRegistration) packet).getPassword());
					Server.getInstance().getInterfaceHandler().displayToLog("Socket Player registerd as: " + trimmedUsername);
				}
				this.connectionSocket.setUsername(trimmedUsername);
			} catch (AuthenticationFailedException exception) {
				Server.getDebugger().log(Level.INFO, "Socket Client failed authentication.", exception);
				this.connectionSocket.sendAuthenticationFailure(exception.getLocalizedMessage());
			}
		} while (this.connectionSocket.getUsername() == null);
		AuthenticationInformation authenticationInformation = Utils.fillAuthenticationInformation();
		try {
			Room playerRoom = Room.getPlayerRoom(this.connectionSocket.getUsername());
			if (playerRoom.isEndGame()) {
				authenticationInformation = this.handleNormalAuthentication(((PacketAuthentication) packet).getRoomType(), authenticationInformation);
			} else {
				authenticationInformation = this.handleGameStartedAuthentication(playerRoom, authenticationInformation);
			}
		} catch (NoSuchElementException exception) {
			Server.getDebugger().log(Level.OFF, DebuggerFormatter.EXCEPTION_MESSAGE, exception);
			authenticationInformation = this.handleNormalAuthentication(((PacketAuthentication) packet).getRoomType(), authenticationInformation);
		}
		this.connectionSocket.sendAuthenticationConfirmation(authenticationInformation);
		return true;
	}

	private AuthenticationInformationLobbySocket handleNormalAuthentication(RoomType roomType, AuthenticationInformation authenticationInformation)
	{
		Room targetRoom = null;
		for (Room room : Server.getInstance().getRooms()) {
			if (room.getGameHandler() == null && room.getRoomType() == roomType && room.getPlayers().size() < roomType.getPlayersNumber()) {
				targetRoom = room;
				break;
			}
		}
		if (targetRoom == null) {
			targetRoom = new Room(roomType);
			Server.getInstance().getRooms().add(targetRoom);
		}
		targetRoom.addPlayer(this.connectionSocket);
		List<String> playerUsernames = new ArrayList<>();
		for (Connection player : targetRoom.getPlayers()) {
			if (player != this.connectionSocket) {
				player.sendRoomEntryOther(this.connectionSocket.getUsername());
			}
			playerUsernames.add(player.getUsername());
		}
		AuthenticationInformationLobbySocket authenticationInformationLobby = new AuthenticationInformationLobbySocket(authenticationInformation, this.connectionSocket.getUsername());
		authenticationInformationLobby.setGameStarted(false);
		authenticationInformationLobby.setRoomInformation(new RoomInformation(targetRoom.getRoomType(), playerUsernames));
		return authenticationInformationLobby;
	}

	private AuthenticationInformationGame handleGameStartedAuthentication(Room playerRoom, AuthenticationInformation authenticationInformation)
	{
		for (Connection connection : playerRoom.getPlayers()) {
			if (connection.getUsername().equals(this.connectionSocket.getUsername())) {
				this.connectionSocket.setPlayer(connection.getPlayer());
				this.connectionSocket.getPlayer().setConnection(this.connectionSocket);
				this.connectionSocket.getPlayer().setOnline(true);
				playerRoom.getPlayers().set(playerRoom.getPlayers().indexOf(connection), this.connectionSocket);
				playerRoom.getPlayers().remove(connection);
				break;
			}
		}
		AuthenticationInformationGame authenticationInformationGame = new AuthenticationInformationGame(authenticationInformation);
		authenticationInformationGame.setGameStarted(true);
		authenticationInformationGame.setExcommunicationTiles(playerRoom.getGameHandler().getBoardHandler().getMatchExcommunicationTilesIndexes());
		authenticationInformationGame.setCouncilPrivilegeRewards(playerRoom.getGameHandler().getBoardHandler().getMatchCouncilPrivilegeRewards());
		authenticationInformationGame.setPlayersIdentifications(playerRoom.getGameHandler().getPlayersIdentifications());
		authenticationInformationGame.setOwnPlayerIndex(this.connectionSocket.getPlayer().getIndex());
		if (playerRoom.getGameHandler().getCurrentPeriod() != null && playerRoom.getGameHandler().getCurrentRound() != null) {
			authenticationInformationGame.setGameInitialized(true);
			authenticationInformationGame.setGameInformation(playerRoom.getGameHandler().generateGameInformation());
			authenticationInformationGame.setPlayersInformation(playerRoom.getGameHandler().generatePlayersInformation());
			authenticationInformationGame.setOwnLeaderCardsHand(playerRoom.getGameHandler().generateLeaderCardsHand(this.connectionSocket.getPlayer()));
			authenticationInformationGame.setTurnPlayerIndex(playerRoom.getGameHandler().getTurnPlayer().getIndex());
			if (playerRoom.getGameHandler().getTurnPlayer().getIndex() != this.connectionSocket.getPlayer().getIndex()) {
				authenticationInformationGame.setAvailableActions(playerRoom.getGameHandler().generateAvailableActions(this.connectionSocket.getPlayer()));
			}
		} else {
			authenticationInformationGame.setGameInitialized(false);
		}
		return authenticationInformationGame;
	}

	synchronized void end()
	{
		this.keepGoing = false;
	}
}
