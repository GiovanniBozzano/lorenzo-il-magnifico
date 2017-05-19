package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.server.*;

import java.io.IOException;
import java.util.logging.Level;

class PacketListener extends Thread
{
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
			switch (packet.getPacketType()) {
				case AUTHENTICATION_CONFIRMATION:
					Connection.handleAuthenticationConfirmation(((PacketAuthenticationConfirmation) packet).getUsername());
					break;
				case AUTHENTICATION_FAILURE:
					Connection.handleAuthenticationFailure(((PacketAuthenticationFailure) packet).getText());
					break;
				case DISCONNECTION_LOG_MESSAGE:
					Connection.handleDisconnectionLogMessage(((PacketDisconnectionLogMessage) packet).getText());
					break;
				case ROOM_LIST:
					Connection.handleRoomList(((PacketRoomList) packet).getRooms());
					break;
				case ROOM_CREATION_FAILURE:
					Connection.handleRoomCreationFailure();
					break;
				case ROOM_ENTRY_CONFIRMATION:
					Connection.handleRoomEntryConfirmation(((PacketRoomEntryConfirmation) packet).getRoomInformations());
					break;
				case ROOM_ENTRY_OTHER:
					Connection.handleRoomEntryOther(((PacketRoomEntryOther) packet).getName());
					break;
				case ROOM_EXIT_OTHER:
					Connection.handleRoomExitOther(((PacketRoomExitOther) packet).getName());
					break;
				case LOG_MESSAGE:
					Connection.handleLogMessage(((PacketLogMessage) packet).getText());
					break;
				case CHAT_MESSAGE:
					Connection.handleChatMessage(((PacketChatMessage) packet).getText());
					break;
				default:
			}
		}
	}

	synchronized void end()
	{
		this.keepGoing = false;
	}
}
