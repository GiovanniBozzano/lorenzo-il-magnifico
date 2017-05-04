package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.socket.packets.client.PacketHandshake;
import it.polimi.ingsw.lim.common.socket.packets.server.*;
import it.polimi.ingsw.lim.common.utils.CommonUtils;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.network.Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
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
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.out.flush();
			this.packetListener = new PacketListener(this);
			this.packetListener.start();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			this.disconnect(true);
		}
	}

	@Override
	public void disconnect(boolean kick)
	{
		super.disconnect(kick);
		if (kick) {
			this.packetListener.close();
		}
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
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
		if (kick && this.packetListener != null && this.packetListener.isAlive()) {
			try {
				this.packetListener.join();
			} catch (InterruptedException exception) {
				Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
				Thread.currentThread().interrupt();
			}
		}
	}

	void sendHandshakeConfirmation()
	{
		new Packet(PacketType.HANDSHAKE_CONFIRMATION).send(this.out);
	}

	@Override
	public void sendRoomList(List<RoomInformations> rooms)
	{
		new PacketRoomList(rooms).send(this.out);
	}

	@Override
	public void sendRoomCreationFailure()
	{
		new Packet(PacketType.ROOM_CREATION_FAILURE).send(this.out);
	}

	@Override
	public void sendRoomEntryConfirmation(RoomInformations roomInformations)
	{
		new PacketRoomEntryConfirmation(roomInformations).send(this.out);
	}

	@Override
	public void sendRoomEntryOther(String name)
	{
		new PacketRoomEntryOther(name).send(this.out);
	}

	@Override
	public void sendRoomExitOther(String name)
	{
		new PacketRoomExitOther(name).send(this.out);
	}

	@Override
	public void sendLogMessage(String text)
	{
		new PacketLogMessage(text).send(this.out);
	}

	@Override
	public void sendChatMessage(String text)
	{
		new PacketChatMessage(text.replaceAll("^\\s+|\\s+$", "")).send(this.out);
	}

	synchronized boolean handleHandshake()
	{
		Packet packet;
		try {
			packet = (Packet) this.in.readObject();
		} catch (ClassNotFoundException | IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			return false;
		}
		if (packet.getPacketType() != PacketType.HANDSHAKE || !((PacketHandshake) packet).getVersion().equals(CommonUtils.VERSION)) {
			this.sendLogMessage("Client version not compatible with the Server.");
			return false;
		}
		String name = ((PacketHandshake) packet).getName().replaceAll("^\\s+|\\s+$", "");
		if (!name.matches("^[\\w\\-]{4,16}$")) {
			return false;
		}
		for (Connection connection : Server.getInstance().getConnections()) {
			if (connection.getName() != null && connection.getName().equals(name)) {
				this.sendLogMessage("Client name is already taken.");
				return false;
			}
		}
		this.setName(name);
		this.sendLogMessage("Connected to server.");
		return true;
	}

	ObjectInputStream getIn()
	{
		return this.in;
	}
}
