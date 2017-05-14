package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.utils.CommonUtils;

public class PacketLogin extends Packet
{
	private final String version;
	private final String username;
	private final String password;

	public PacketLogin(String username, String password)
	{
		super(PacketType.LOGIN);
		this.version = CommonUtils.VERSION;
		this.username = username;
		this.password = password;
	}

	public String getVersion()
	{
		return this.version;
	}

	public String getUsername()
	{
		return this.username;
	}

	public String getPassword()
	{
		return this.password;
	}
}
