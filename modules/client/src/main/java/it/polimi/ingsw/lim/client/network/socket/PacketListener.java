package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.client.Client;
import it.polimi.ingsw.lim.client.network.Connection;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.socket.packets.server.*;
import it.polimi.ingsw.lim.common.utils.LogFormatter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

public class PacketListener extends Thread
{
	private final String ip;
	private final int port;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private boolean keepGoing = true;

	public PacketListener(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void run()
	{
		try {
			this.socket = new Socket(this.ip, this.port);
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
			this.in = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException exception) {
			Client.getInstance().getWindowInformations().getStage().getScene().getRoot().setDisable(false);
			Client.getLogger().log(Level.INFO, "Could not connect to host.", exception);
			return;
		}
		Client.getInstance().setConnected(true);
		Connection.sendHandshake();
		while (this.keepGoing) {
			Packet packet;
			try {
				packet = (Packet) this.in.readObject();
			} catch (ClassNotFoundException | IOException exception) {
				if (!this.keepGoing) {
					return;
				}
				Client.getLogger().log(Level.INFO, "The Server closed the connection.", exception);
				Client.getInstance().disconnect(false);
				return;
			}
			if (packet == null) {
				Client.getInstance().disconnect(false);
				return;
			}
			switch (packet.getPacketType()) {
				case HANDSHAKE_CONFIRMATION:
					Connection.handleHandshakeConfirmation();
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

	public synchronized void close()
	{
		this.keepGoing = false;
		try {
			if (this.in != null) {
				this.in.close();
			}
			if (this.out != null) {
				this.out.close();
			}
			if (this.socket != null) {
				this.socket.close();
			}
		} catch (IOException exception) {
			Client.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public ObjectOutputStream getOut()
	{
		return this.out;
	}
}
