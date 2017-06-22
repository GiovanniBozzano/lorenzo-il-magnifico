package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameExcommunicationPlayerChoice extends Packet
{
	private final boolean excommunicated;

	public PacketGameExcommunicationPlayerChoice(boolean excommunicated)
	{
		super(PacketType.GAME_EXCOMMUNICATION_PLAYER_CHOICE);
		this.excommunicated = excommunicated;
	}

	public boolean isExcommunicated()
	{
		return this.excommunicated;
	}
}
