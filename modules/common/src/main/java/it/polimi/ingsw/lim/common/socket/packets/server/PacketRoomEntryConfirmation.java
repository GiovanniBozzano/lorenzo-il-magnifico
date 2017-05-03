package it.polimi.ingsw.lim.common.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

public class PacketRoomEntryConfirmation extends Packet
{
	private final RoomInformations roomInformations;

	public PacketRoomEntryConfirmation(RoomInformations roomInformations)
	{
		super(PacketType.ROOM_ENTRY_CONFIRMATION);
		this.roomInformations = roomInformations;
	}

	public RoomInformations getRoomInformations()
	{
		return this.roomInformations;
	}
}
