package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketLoginFailure extends Packet
{
	private final String text;

	public PacketLoginFailure(String text)
	{
		super(PacketType.LOGIN_FAILURE);
		this.text = text;
	}

	public String getText()
	{
		return this.text;
	}
}
