package it.polimi.ingsw.lim.common.network.socket.packets;

import it.polimi.ingsw.lim.common.enums.PacketType;

public class PacketChatMessage extends Packet
{
	private final String text;

	public PacketChatMessage(String text)
	{
		super(PacketType.CHAT_MESSAGE);
		this.text = text;
	}

	public String getText()
	{
		return this.text;
	}
}
