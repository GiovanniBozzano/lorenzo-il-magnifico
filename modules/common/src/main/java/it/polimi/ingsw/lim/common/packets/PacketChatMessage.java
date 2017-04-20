package it.polimi.ingsw.lim.common.packets;

import it.polimi.ingsw.lim.common.enums.FontType;
import it.polimi.ingsw.lim.common.enums.PacketType;

public class PacketChatMessage extends Packet
{
	private final String text;
	private final FontType fontType;

	public PacketChatMessage(String text, FontType fontType)
	{
		super(PacketType.CHAT_MESSAGE);
		this.text = text;
		this.fontType = fontType;
	}

	public String getText()
	{
		return this.text;
	}

	public FontType getFontType()
	{
		return this.fontType;
	}
}
