package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.network.AuthenticationInformation;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationGame;

public class AuthenticationInformationGameRMI extends AuthenticationInformationGame
{
	@SuppressWarnings("squid:S1948") private final IClientSession clientSession;

	public AuthenticationInformationGameRMI(AuthenticationInformation authenticationInformation, IClientSession clientSession)
	{
		super(authenticationInformation);
		this.clientSession = clientSession;
	}

	public IClientSession getClientSession()
	{
		return this.clientSession;
	}
}
