package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.util.ArrayList;
import java.util.List;

public class PacketRoomList extends Packet
{
	private final List<RoomInformations> rooms;

	public PacketRoomList(List<RoomInformations> rooms)
	{
		super(PacketType.ROOM_LIST);
		this.rooms = new ArrayList<>(rooms);
	}

	public List<RoomInformations> getRooms()
	{
		return this.rooms;
	}
}
