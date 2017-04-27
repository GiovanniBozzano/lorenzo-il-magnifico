package it.polimi.ingsw.lim.common.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

import java.util.ArrayList;
import java.util.List;

public class PacketRoomList extends Packet
{
	private ArrayList<RoomInformations> rooms;

	public PacketRoomList()
	{
		super(PacketType.ROOM_LIST);
	}

	public List<RoomInformations> getRooms()
	{
		return this.rooms;
	}

	public void setRooms(List<RoomInformations> rooms)
	{
		this.rooms = new ArrayList<>(rooms);
	}
}
