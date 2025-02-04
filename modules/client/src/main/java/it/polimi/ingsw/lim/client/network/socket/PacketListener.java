package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.server.*;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private static final Map<PacketType, IPacketHandler> PACKET_HANDLERS = new EnumMap<>(PacketType.class);

	static {
		PacketListener.PACKET_HANDLERS.put(PacketType.AUTHENTICATION_CONFIRMATION, packet -> ((ConnectionHandlerSocket) Client.getInstance().getConnectionHandler()).handleAuthenticationConfirmation(((PacketAuthenticationConfirmation) packet).getAuthenticationInformation()));
		PacketListener.PACKET_HANDLERS.put(PacketType.AUTHENTICATION_FAILURE, packet -> Client.getInstance().getInterfaceHandler().handleAuthenticationFailed(((PacketAuthenticationFailure) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.CHAT_MESSAGE, packet -> Client.getInstance().getConnectionHandler().handleChatMessage(((PacketChatMessage) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.DISCONNECTION_LOG_MESSAGE, packet -> (Client.getInstance().getConnectionHandler()).handleDisconnectionLogMessage(((PacketDisconnectionLogMessage) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_ACTION_FAILED, packet -> ((ConnectionHandlerSocket) Client.getInstance().getConnectionHandler()).handleGameActionFailed(((PacketGameActionFailed) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_DISCONNECTION_OTHER, packet -> Client.getInstance().getConnectionHandler().handleGameDisconnectionOther(((PacketGameDisconnectionOther) packet).getPlayerIndex()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_ENDED, packet -> Client.getInstance().getConnectionHandler().handleGameEnded(((PacketGameEnded) packet).getPlayersScores(), ((PacketGameEnded) packet).getPlayerIndexesVictoryPointsRecord()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_EXCOMMUNICATION_CHOICE_OTHER, packet -> Client.getInstance().getConnectionHandler().handleGameExcommunicationChoiceOther());
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_EXCOMMUNICATION_CHOICE_REQUEST, packet -> Client.getInstance().getConnectionHandler().handleGameExcommunicationChoiceRequest(((PacketGameExcommunicationChoiceRequest) packet).getPeriod()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_LEADER_CARD_CHOICE_REQUEST, packet -> Client.getInstance().getConnectionHandler().handleGameLeaderCardChoiceRequest(((PacketGameLeaderCardChoiceRequest) packet).getAvailableLeaderCardsIndexes()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_LOG_MESSAGE, packet -> Client.getInstance().getConnectionHandler().handleGameLogMessage(((PacketGameLogMessage) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_PERSONAL_BONUS_TILE_CHOICE_OTHER, packet -> Client.getInstance().getConnectionHandler().handleGamePersonalBonusTileChoiceOther(((PacketGamePersonalBonusTileChoiceOther) packet).getChoicePlayerIndex()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_PERSONAL_BONUS_TILE_CHOICE_REQUEST, packet -> Client.getInstance().getConnectionHandler().handleGamePersonalBonusTileChoiceRequest(((PacketGamePersonalBonusTileChoiceRequest) packet).getAvailablePersonalBonusTilesIndexes()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_PERSONAL_BONUS_TILE_CHOSEN, packet -> Client.getInstance().getConnectionHandler().handleGamePersonalBonusTileChosen(((PacketGamePersonalBonusTileChosen) packet).getChoicePlayerIndex()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_STARTED, packet -> Client.getInstance().getConnectionHandler().handleGameStarted(((PacketGameStarted) packet).getExcommunicationTiles(), ((PacketGameStarted) packet).getCouncilPrivilegeRewards(), ((PacketGameStarted) packet).getPlayersIdentifications(), ((PacketGameStarted) packet).getOwnPlayerIndex()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_TIMER, packet -> Client.getInstance().getConnectionHandler().handleGameTimer(((PacketGameTimer) packet).getTimer()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_UPDATE, packet -> Client.getInstance().getConnectionHandler().handleGameUpdate(((PacketGameUpdate) packet).getGameInformation(), ((PacketGameUpdate) packet).getPlayersInformation(), ((PacketGameUpdate) packet).getOwnLeaderCardsHand(), ((PacketGameUpdate) packet).getAvailableActions()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_UPDATE_EXPECTED_ACTION, packet -> Client.getInstance().getConnectionHandler().handleGameUpdateExpectedAction(((PacketGameUpdateExpectedAction) packet).getGameInformation(), ((PacketGameUpdateExpectedAction) packet).getPlayersInformation(), ((PacketGameUpdateExpectedAction) packet).getOwnLeaderCardsHand(), ((PacketGameUpdateExpectedAction) packet).getExpectedAction()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_UPDATE_OTHER_TURN, packet -> Client.getInstance().getConnectionHandler().handleGameUpdateOtherTurn(((PacketGameUpdateOtherTurn) packet).getGameInformation(), ((PacketGameUpdateOtherTurn) packet).getPlayersInformation(), ((PacketGameUpdateOtherTurn) packet).getOwnLeaderCardsHand(), ((PacketGameUpdateOtherTurn) packet).getTurnPlayerIndex()));
		PacketListener.PACKET_HANDLERS.put(PacketType.HEARTBEAT, packet -> {
			// This method is empty because it is only called to check the connection.
		});
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_ENTRY_OTHER, packet -> Client.getInstance().getConnectionHandler().handleRoomEntryOther(((PacketRoomEntryOther) packet).getName()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_EXIT_OTHER, packet -> Client.getInstance().getConnectionHandler().handleRoomExitOther(((PacketRoomExitOther) packet).getName()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_TIMER, packet -> Client.getInstance().getConnectionHandler().handleRoomTimer(((PacketRoomTimer) packet).getTimer()));
	}

	private volatile boolean keepGoing = true;

	@Override
	public void run()
	{
		while (this.keepGoing) {
			Packet packet;
			try {
				packet = (Packet) ((ConnectionHandlerSocket) Client.getInstance().getConnectionHandler()).getIn().readObject();
			} catch (ClassNotFoundException | IOException exception) {
				if (!this.keepGoing) {
					return;
				}
				Client.getDebugger().log(Level.INFO, "The Server closed the connection.", exception);
				Client.getInstance().disconnect(false, false);
				return;
			}
			if (packet == null) {
				Client.getInstance().disconnect(false, false);
				return;
			}
			PacketListener.PACKET_HANDLERS.get(packet.getPacketType()).execute(packet);
		}
	}

	synchronized void end()
	{
		this.keepGoing = false;
	}
}
