package it.polimi.ingsw.lim.common.network.socket;

import it.polimi.ingsw.lim.common.network.AuthenticationInformation;
import it.polimi.ingsw.lim.common.network.AuthenticationInformationLobby;

public class AuthenticationInformationLobbySocket extends AuthenticationInformationLobby
{
	private String username;

	public AuthenticationInformationLobbySocket(AuthenticationInformation authenticationInformation, String username)
	{
		super(authenticationInformation);
		this.username = username;
	}

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
}
