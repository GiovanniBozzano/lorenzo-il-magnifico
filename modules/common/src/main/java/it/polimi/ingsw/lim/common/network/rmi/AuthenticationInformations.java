package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.game.RoomInformations;

import java.io.Serializable;

public class AuthenticationInformations implements Serializable
{
	@SuppressWarnings("squid:S1948") private final IClientSession clientSession;
	private final RoomInformations roomInformations;

	public AuthenticationInformations(IClientSession clientSession, RoomInformations roomInformations)
	{
		this.clientSession = clientSession;
		this.roomInformations = roomInformations;
	}

	public IClientSession getClientSession()
	{
		return this.clientSession;
	}

	public RoomInformations getRoomInformations()
	{
		return this.roomInformations;
	}
}
