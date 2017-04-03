package it.polimi.ingsw.lim.server;

import it.polimi.ingsw.lim.common.Instance;
import it.polimi.ingsw.lim.common.utils.CardHelper;
import it.polimi.ingsw.lim.common.enums.EnumSide;

public class Server extends Instance
{
	public Server()
	{
		Server.instance = this;
		this.side = EnumSide.SERVER;
		this.cardHelper = new CardHelper();
	}

	public static Server getInstance()
	{
		return (Server) instance;
	}
}
