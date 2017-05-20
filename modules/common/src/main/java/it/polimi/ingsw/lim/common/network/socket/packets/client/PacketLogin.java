package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.RoomType;

public class PacketLogin extends PacketAuthentication
{
	public PacketLogin(String username, String password, RoomType roomType)
	{
		super(PacketType.LOGIN, username, password, roomType);
	}
}
