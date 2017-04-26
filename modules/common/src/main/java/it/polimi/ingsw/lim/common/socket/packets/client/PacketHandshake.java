package it.polimi.ingsw.lim.common.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.socket.packets.Packet;
import it.polimi.ingsw.lim.common.utils.Constants;

public class PacketHandshake extends Packet
{
	private final String version;
	private final String name;

	public PacketHandshake(String name)
	{
		super(PacketType.HANDSHAKE);
		this.version = Constants.VERSION;
		this.name = name;
	}

	public String getVersion()
	{
		return this.version;
	}

	public String getName()
	{
		return this.name;
	}
}
