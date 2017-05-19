package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.server.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

class PacketListener extends Thread
{
	private static final Map<PacketType, IPacketHandler> PACKET_HANDLERS = new HashMap<>();

	static {
		PacketListener.PACKET_HANDLERS.put(PacketType.AUTHENTICATION_CONFIRMATION, (packet) -> Client.getInstance().getConnectionHandler().handleAuthenticationConfirmation(((PacketAuthenticationConfirmation) packet).getUsername()));
		PacketListener.PACKET_HANDLERS.put(PacketType.AUTHENTICATION_FAILURE, (packet) -> Client.getInstance().getConnectionHandler().handleAuthenticationFailure(((PacketAuthenticationFailure) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.DISCONNECTION_LOG_MESSAGE, (packet) -> ((ConnectionHandlerSocket) Client.getInstance().getConnectionHandler()).handleDisconnectionLogMessage(((PacketDisconnectionLogMessage) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_LIST, (packet) -> Client.getInstance().getConnectionHandler().handleRoomList(((PacketRoomList) packet).getRooms()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_CREATION_FAILURE, (packet) -> Client.getInstance().getConnectionHandler().handleRoomCreationFailure());
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_ENTRY_CONFIRMATION, (packet) -> Client.getInstance().getConnectionHandler().handleRoomEntryConfirmation(((PacketRoomEntryConfirmation) packet).getRoomInformations()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_ENTRY_OTHER, (packet) -> Client.getInstance().getConnectionHandler().handleRoomEntryOther(((PacketRoomEntryOther) packet).getName()));
		PacketListener.PACKET_HANDLERS.put(PacketType.ROOM_EXIT_OTHER, (packet) -> Client.getInstance().getConnectionHandler().handleRoomExitOther(((PacketRoomExitOther) packet).getName()));
		PacketListener.PACKET_HANDLERS.put(PacketType.LOG_MESSAGE, (packet) -> Client.getInstance().getConnectionHandler().handleLogMessage(((PacketLogMessage) packet).getText()));
		PacketListener.PACKET_HANDLERS.put(PacketType.CHAT_MESSAGE, (packet) -> Client.getInstance().getConnectionHandler().handleChatMessage(((PacketChatMessage) packet).getText()));
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
				Client.getLogger().log(Level.INFO, "The Server closed the connection.", exception);
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
