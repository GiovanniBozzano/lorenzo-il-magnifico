package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

public abstract class PacketAuthentication extends Packet
{
	private final String version;
	private final String username;
	private final String password;
	private final RoomType roomType;

	public PacketAuthentication(PacketType packetType, String username, String password, RoomType roomType)
	{
		super(packetType);
		this.version = CommonUtils.VERSION;
		this.username = username;
		this.password = password;
		this.roomType = roomType;
	}

	public String getVersion()
	{
		return this.version;
	}

	public String getUsername()
	{
		return this.username;
	}

	public String getPassword()
	{
		return this.password;
	}

	public RoomType getRoomType()
	{
		return this.roomType;
	}
}
