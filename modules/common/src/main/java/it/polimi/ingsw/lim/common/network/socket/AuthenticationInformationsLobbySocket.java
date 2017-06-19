package it.polimi.ingsw.lim.common.network.socket;

import it.polimi.ingsw.lim.common.network.AuthenticationInformationsLobby;

public class AuthenticationInformationsLobbySocket extends AuthenticationInformationsLobby
{
	private String username;

	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
}
