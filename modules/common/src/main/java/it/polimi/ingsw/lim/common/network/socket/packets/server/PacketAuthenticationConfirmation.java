package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.AuthenticationInformation;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketAuthenticationConfirmation extends Packet
{
	private final AuthenticationInformation authenticationInformation;

	public PacketAuthenticationConfirmation(AuthenticationInformation authenticationInformation)
	{
		super(PacketType.AUTHENTICATION_CONFIRMATION);
		this.authenticationInformation = authenticationInformation;
	}

	public AuthenticationInformation getAuthenticationInformation()
	{
		return this.authenticationInformation;
	}
}
