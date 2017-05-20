package it.polimi.ingsw.lim.server.network.socket;

import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

@FunctionalInterface public interface IPacketHandler
{
	void execute(ConnectionSocket connectionSocket, Packet packet);
}
