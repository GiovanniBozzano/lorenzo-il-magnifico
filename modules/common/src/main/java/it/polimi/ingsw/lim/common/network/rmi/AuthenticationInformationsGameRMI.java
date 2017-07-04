package it.polimi.ingsw.lim.common.network.rmi;

import it.polimi.ingsw.lim.common.network.AuthenticationInformations;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationsGame;

public class AuthenticationInformationsGameRMI extends AuthenticationInformationsGame
{
	@SuppressWarnings("squid:S1948") private IClientSession clientSession;

	public AuthenticationInformationsGameRMI(AuthenticationInformations authenticationInformations, IClientSession clientSession)
	{
		super(authenticationInformations);
		this.clientSession = clientSession;
	}

	public IClientSession getClientSession()
	{
		return this.clientSession;
	}
}
