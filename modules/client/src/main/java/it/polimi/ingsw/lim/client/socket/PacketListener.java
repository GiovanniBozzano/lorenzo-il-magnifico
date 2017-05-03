package it.polimi.ingsw.lim.client.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.gui.ControllerLobby;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.socket.packets.server.PacketLogMessage;
import it.polimi.ingsw.lim.common.socket.packets.server.PacketRoomCreationConfirmation;
import it.polimi.ingsw.lim.common.socket.packets.server.PacketRoomEntryConfirmation;
import it.polimi.ingsw.lim.common.socket.packets.server.PacketRoomList;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.io.IOException;
import java.util.logging.Level;

public class PacketListener extends Thread
{
	private boolean keepGoing = true;

	@Override
	public void run()
	{
		while (this.keepGoing) {
			try {
				Packet packet = (Packet) Client.getInstance().getIn().readObject();
				if (packet == null) {
					Client.getInstance().disconnect();
					return;
				}
				switch (packet.getPacketType()) {
					case HANDSHAKE_CONFIRMATION:
						PacketListener.handleHandshakeConfirmation();
						break;
					case ROOM_LIST:
						PacketListener.handleRoomList((PacketRoomList) packet);
						break;
					case ROOM_CREATION_CONFIRMATION:
						PacketListener.handleRoomCreationConfirmation(((PacketRoomCreationConfirmation) packet).getRoomInformations());
						break;
					case ROOM_ENTRY_CONFIRMATION:
						PacketListener.handleRoomEntryConfirmation(((PacketRoomEntryConfirmation) packet).getRoomInformations());
						break;
					case LOG_MESSAGE:
						PacketListener.handleLogMessage((PacketLogMessage) packet);
						break;
					case CHAT_MESSAGE:
						PacketListener.handleChatMessage((PacketChatMessage) packet);
						break;
					default:
				}
			} catch (ClassNotFoundException | IOException exception) {
				Client.getLogger().log(Level.INFO, "The connection has been closed.", exception);
				if (Client.getInstance() != null) {
					Client.getInstance().disconnect();
				}
			}
		}
	}

	public static void handleHandshakeConfirmation()
	{
		Client.getInstance().setNewWindow("/fxml/SceneLobby.fxml", new Thread(() -> Client.getInstance().sendRequestRoomList()));
	}

	public static void handleRoomList(PacketRoomList packet)
	{
		if (!(Client.getInstance().getWindowInformations().getController() instanceof ControllerLobby)) {
			return;
		}
		for (RoomInformations roomInformations : packet.getRooms()) {
			((ControllerLobby) Client.getInstance().getWindowInformations().getController()).getRoomsListView().getItems().add(roomInformations);
		}
	}

	public static void handleRoomCreationConfirmation(RoomInformations roomInformations)
	{
	}

	public static void handleRoomEntryConfirmation(RoomInformations roomInformation)
	{
	}

	public static void handleChatMessage(PacketChatMessage packet)
	{
		Client.getLogger().log(Level.INFO, packet.getText());
	}

	public static void handleLogMessage(PacketLogMessage packet)
	{
		Client.getLogger().log(Level.INFO, packet.getText());
	}

	public synchronized void close()
	{
		this.keepGoing = false;
	}
}
