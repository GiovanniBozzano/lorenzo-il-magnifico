package it.polimi.ingsw.lim.common.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

public class PacketRoomCreationConfirmation extends Packet
{
	private final RoomInformations roomInformations;

	public PacketRoomCreationConfirmation(RoomInformations roomInformations)
	{
		super(PacketType.ROOM_CREATION_CONFIRMATION);
		this.roomInformations = roomInformations;
	}

	public RoomInformations getRoomInformations()
	{
		return this.roomInformations;
	}
}
