package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketAuthenticationConfirmation extends Packet
{
	private final String username;

	public PacketAuthenticationConfirmation(String username)
	{
		super(PacketType.AUTHENTICATION_CONFIRMATION);
		this.username = username;
	}

	public String getUsername()
	{
		return this.username;
	}
}
