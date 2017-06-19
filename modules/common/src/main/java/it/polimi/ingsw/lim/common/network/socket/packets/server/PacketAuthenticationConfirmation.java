package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.AuthenticationInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketAuthenticationConfirmation extends Packet
{
	private final AuthenticationInformations authenticationInformations;

	public PacketAuthenticationConfirmation(AuthenticationInformations authenticationInformations)
	{
		super(PacketType.AUTHENTICATION_CONFIRMATION);
		this.authenticationInformations = authenticationInformations;
	}

	public AuthenticationInformations getAuthenticationInformations()
	{
		return this.authenticationInformations;
	}
}
