package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;
import it.polimi.ingsw.lim.common.utils.RoomInformations;

public class PacketAuthenticationConfirmation extends Packet
{
	private final String username;
	private final RoomInformations roomInformations;

	public PacketAuthenticationConfirmation(String username, RoomInformations roomInformations)
	{
		super(PacketType.AUTHENTICATION_CONFIRMATION);
		this.username = username;
		this.roomInformations = roomInformations;
	}

	public String getUsername()
	{
		return this.username;
	}

	public RoomInformations getRoomInformations()
	{
		return this.roomInformations;
	}
}
