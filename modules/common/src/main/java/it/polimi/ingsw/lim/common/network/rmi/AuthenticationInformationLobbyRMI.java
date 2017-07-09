package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.network.AuthenticationInformation;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationLobby;

public class AuthenticationInformationLobbyRMI extends AuthenticationInformationLobby
{
	@SuppressWarnings("squid:S1948") private IClientSession clientSession;

	public AuthenticationInformationLobbyRMI(AuthenticationInformation authenticationInformation, IClientSession clientSession)
	{
		super(authenticationInformation);
		this.clientSession = clientSession;
	}

	public IClientSession getClientSession()
	{
		return this.clientSession;
	}
}
