package it.polimi.ingsw.lim.server.socket;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.socket.packets.PacketChatMessage;
import it.polimi.ingsw.lim.common.socket.packets.client.PacketHandshake;
import it.polimi.ingsw.lim.common.socket.packets.server.PacketLogMessage;
import it.polimi.ingsw.lim.common.socket.packets.server.PacketRoomList;
import it.polimi.ingsw.lim.common.utils.Constants;
import it.polimi.ingsw.lim.common.utils.LogFormatter;
import it.polimi.ingsw.lim.common.utils.RoomInformations;
import it.polimi.ingsw.lim.server.IConnection;
import it.polimi.ingsw.lim.server.Room;
import it.polimi.ingsw.lim.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ConnectionSocket implements IConnection
{
	private final Integer id;
	private String name;
	private final Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	ConnectionSocket(int id, Socket socket)
	{
		this.id = id;
		this.socket = socket;
		try {
			this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.out.flush();
			new PacketListener(this).start();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			disconnect();
		}
	}

	@Override
	public void disconnect()
	{
		Server.getInstance().getConnections().remove(this);
		try {
			if (this.out != null) {
				this.out.close();
			}
			if (this.in != null) {
				this.in.close();
			}
			this.socket.close();
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	void sendHandshakeConfirmation()
	{
		try {
			this.out.writeObject(new Packet(PacketType.HANDSHAKE_CORRECT));
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendRoomList()
	{
		List<RoomInformations> rooms = new ArrayList<>();
		for (Room room : Server.getInstance().getRooms()) {
			List<String> playerNames = new ArrayList<>();
			for (IConnection player : room.getPlayers()) {
				playerNames.add(player.getName());
			}
			rooms.add(new RoomInformations(room.getId(), room.getName(), playerNames));
		}
		try {
			PacketRoomList packetRoomList = new PacketRoomList();
			packetRoomList.setRooms(rooms);
			this.out.writeObject(packetRoomList);
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	public void sendRoomEnterConfirmation(RoomInformations roomInformations)
	{
	}

	public void sendRoomExitConfirmation()
	{
	}

	@Override
	public void sendLogMessage(String text)
	{
		try {
			this.out.writeObject(new PacketLogMessage(text));
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	@Override
	public void sendChatMessage(String text)
	{
		try {
			this.out.writeObject(new PacketChatMessage(text));
		} catch (IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
		}
	}

	boolean handleHandshake()
	{
		try {
			Packet packet = (Packet) this.in.readObject();
			if (packet.getPacketType() != PacketType.HANDSHAKE || !((PacketHandshake) packet).getVersion().equals(Constants.VERSION)) {
				this.sendLogMessage("Client version not compatible with the Server.");
				return false;
			}
			if (((PacketHandshake) packet).getName().length() == 0) {
				this.sendLogMessage("Client name is empty.");
				return false;
			}
			for (IConnection connection : Server.getInstance().getConnections()) {
				if (connection.getName() != null && connection.getName().equals(((PacketHandshake) packet).getName())) {
					this.sendLogMessage("Client name is already taken.");
					return false;
				}
			}
			this.name = ((PacketHandshake) packet).getName();
			this.sendLogMessage("Connected to server.");
			return true;
		} catch (ClassNotFoundException | IOException exception) {
			Server.getLogger().log(Level.SEVERE, LogFormatter.EXCEPTION_MESSAGE, exception);
			return false;
		}
	}

	@Override
	public void handleRequestRoomList()
	{
		this.sendRoomList();
	}

	@Override
	public void handleRoomEntry(int id)
	{
		for (Room room : Server.getInstance().getRooms()) {
			if (room.getPlayers().contains(this)) {
				return;
			}
		}
		Room targetRoom = null;
		for (Room room : Server.getInstance().getRooms()) {
			if (room.getId() == id) {
				targetRoom = room;
				break;
			}
		}
		if (targetRoom == null || targetRoom.getPlayers().contains(this) || targetRoom.getPlayers().size() > 4) {
			this.disconnect();
			return;
		}
		targetRoom.getPlayers().add(this);
		Server.getInstance().broadcastRoomsUpdate();
	}

	@Override
	public void handleRoomExit()
	{
		for (Room room : Server.getInstance().getRooms()) {
			room.getPlayers().remove(this);
		}
		Server.getInstance().broadcastRoomsUpdate();
	}

	@Override
	public void handleChatMessage(String text)
	{
		for (IConnection otherConnection : Server.getInstance().getConnections()) {
			if (otherConnection != this) {
				otherConnection.sendChatMessage(this.name + ": " + text);
			}
		}
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	public ObjectInputStream getIn()
	{
		return this.in;
	}

	public ObjectOutputStream getOut()
	{
		return this.out;
	}
}
