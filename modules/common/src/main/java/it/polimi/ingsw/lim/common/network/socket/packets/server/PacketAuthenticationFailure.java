package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketAuthenticationFailure extends Packet
{
	private final String text;

	public PacketAuthenticationFailure(String text)
	{
		super(PacketType.AUTHENTICATION_FAILURE);
		this.text = text;
	}

	public String getText()
	{
		return this.text;
	}
}
