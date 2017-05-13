package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.network.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.network.socket.packets.server.*;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;
import it.polimi.ingsw.lim.server.utils.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ConnectionSocket extends Connection
{
	private final Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private PacketListener packetListener;

	public ConnectionSocket(int id, Socket socket)
	{
		super(id);
		this.socket = socket;
		try {
			this.socket.setSoTimeout(12000);
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.out.flush();
			this.packetListener = new PacketListener(this);
			this.packetListener.start();
			this.getHeartbeat().scheduleAtFixedRate(this::sendHeartbeat, 0L, 3000L, TimeUnit.MILLISECONDS);
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			this.disconnect(true, null);
		}
	}

	@Override
	public synchronized void disconnect(boolean isBeingKicked, String message)
	{
		super.disconnect(isBeingKicked, message);
		if (message != null) {
			this.sendDisconnectionLogMessage(message);
			try {
				this.socket.setSoTimeout(3000);
				Packet packet;
				do {
					packet = (Packet) this.in.readObject();
				}
				while (packet.getPacketType() != PacketType.DISCONNECTION_ACKNOWLEDGEMENT);
			} catch (IOException | ClassNotFoundException exception) {
				Server.getLogger().log(Level.INFO, "Connection acknowledgement failed.", exception);
			}
		}
		if (isBeingKicked) {
			this.packetListener.close();
		}
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		if (isBeingKicked) {
			try {
				this.packetListener.join();
			} catch (InterruptedException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				Thread.currentThread().interrupt();
			}
		}
		Utils.displayToLog("Socket Client " + this.getId() + (this.getName() != null ? " : " + this.getName() : "") + " disconnected.");
	}

	@Override
	public synchronized void sendHeartbeat()
	{
		new Packet(PacketType.HEARTBEAT).send(this.out);
	}

	synchronized void sendHandshakeConfirmation()
	{
		new Packet(PacketType.HANDSHAKE_CONFIRMATION).send(this.out);
	}

	@Override
	public synchronized void sendRoomList(List<RoomInformations> rooms)
	{
		new PacketRoomList(rooms).send(this.out);
	}

	@Override
	public synchronized void sendRoomCreationFailure()
	{
		new Packet(PacketType.ROOM_CREATION_FAILURE).send(this.out);
	}

	@Override
	public synchronized void sendRoomEntryConfirmation(RoomInformations roomInformations)
	{
		new PacketRoomEntryConfirmation(roomInformations).send(this.out);
	}

	@Override
	public synchronized void sendRoomEntryOther(String name)
	{
		new PacketRoomEntryOther(name).send(this.out);
	}

	@Override
	public synchronized void sendRoomExitOther(String name)
	{
		new PacketRoomExitOther(name).send(this.out);
	}

	@Override
	public synchronized void sendLogMessage(String text)
	{
		new PacketLogMessage(text).send(this.out);
	}

	private synchronized void sendDisconnectionLogMessage(String text)
	{
		new PacketDisconnectionLogMessage(text).send(this.out);
	}

	@Override
	public synchronized void sendChatMessage(String text)
	{
		new PacketChatMessage(text.replaceAll("^\\s+|\\s+$", "")).send(this.out);
	}

	ObjectInputStream getIn()
	{
		return this.in;
	}
}
