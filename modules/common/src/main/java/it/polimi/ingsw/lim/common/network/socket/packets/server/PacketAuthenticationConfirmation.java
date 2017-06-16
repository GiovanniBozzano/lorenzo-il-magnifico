package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.AuthenticationInformationsSocket;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketAuthenticationConfirmation extends Packet
{
	private final AuthenticationInformationsSocket authenticationInformations;

	public PacketAuthenticationConfirmation(AuthenticationInformationsSocket authenticationInformations)
	{
		super(PacketType.AUTHENTICATION_CONFIRMATION);
		this.authenticationInformations = authenticationInformations;
	}

	public AuthenticationInformationsSocket getAuthenticationInformations()
	{
		return this.authenticationInformations;
	}
}
