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
		PacketListener.PACKET_HANDLERS.put(PacketType.HEARTBEAT, packet -> {
			// This method is empty because it is only called to check the connection.
		});
		PacketListener.PACKET_HANDLERS.put(PacketType.AUTHENTICATION_CONFIRMATION, packet -> ((ConnectionHandlerSocket) Client.getInstance().getConnectionHandler()).handleAuthenticationConfirmation(((PacketAuthenticationConfirmation) packet).getUsername(), ((PacketAuthenticationConfirmation) packet).getRoomInformations()));
		PacketListener.PACKET_HANDLERS.put(PacketType.AUTHENTICATION_FAILURE, packet -> ((ConnectionHandlerSocket) Client.getInstance().getConnectionHandler()).handleAuthenticationFailure(((PacketAuthenticationFailure) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.DISCONNECTION_LOG_MESSAGE, packet -> ((ConnectionHandlerSocket) Client.getInstance().getConnectionHandler()).handleDisconnectionLogMessage(((PacketDisconnectionLogMessage) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_ENTRY_OTHER, packet -> Client.getInstance().getConnectionHandler().handleRoomEntryOther(((PacketRoomEntryOther) packet).getName()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_EXIT_OTHER, packet -> Client.getInstance().getConnectionHandler().handleRoomExitOther(((PacketRoomExitOther) packet).getName()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_TIMER, packet -> Client.getInstance().getConnectionHandler().handleRoomTimer(((PacketRoomTimer) packet).getTimer()));
		PacketListener.PACKET_HANDLERS.put(PacketType.GAME_STARTED, packet -> Client.getInstance().getConnectionHandler().handleGameStarted(((PacketGameStarted) packet).getRoomInformations()));
		PacketListener.PACKET_HANDLERS.put(PacketType.LOG_MESSAGE, packet -> Client.getInstance().getConnectionHandler().handleLogMessage(((PacketLogMessage) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.CHAT_MESSAGE, packet -> Client.getInstance().getConnectionHandler().handleChatMessage(((PacketChatMessage) packet).getText()));
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
