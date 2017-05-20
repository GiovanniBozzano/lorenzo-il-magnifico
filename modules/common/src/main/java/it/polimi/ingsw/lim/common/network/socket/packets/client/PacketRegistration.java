package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.RoomType;

public class PacketRegistration extends PacketAuthentication
{
	public PacketRegistration(String username, String password, RoomType roomType)
	{
		super(PacketType.REGISTRATION, username, password, roomType);
	}
}
