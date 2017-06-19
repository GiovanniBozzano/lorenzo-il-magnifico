package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.network.AuthenticationInformationsLobby;

public class AuthenticationInformationsLobbyRMI extends AuthenticationInformationsLobby
{
	@SuppressWarnings("squid:S1948") private IClientSession clientSession;

	public IClientSession getClientSession()
	{
		return this.clientSession;
	}

	public void setClientSession(IClientSession clientSession)
	{
		this.clientSession = clientSession;
	}
}
