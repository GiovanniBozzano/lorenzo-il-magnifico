package it.polimi.ingsw.lim.client.network.socket;

import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

@FunctionalInterface public interface IPacketHandler
{
	void execute(Packet packet);
}
