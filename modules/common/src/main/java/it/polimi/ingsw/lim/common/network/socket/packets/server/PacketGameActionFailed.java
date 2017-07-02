package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameActionFailed extends Packet
{
	private final String text;

	public PacketGameActionFailed(String text)
	{
		super(PacketType.GAME_ACTION_FAILED);
		this.text = text;
	}

	public String getText()
	{
		return this.text;
	}
}
