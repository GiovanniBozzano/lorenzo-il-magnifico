package it.polimi.ingsw.lim.common.packets;

import it.polimi.ingsw.lim.common.enums.FontType;
import it.polimi.ingsw.lim.common.enums.PacketType;

import java.io.Serializable;

public class PacketChatMessage extends Packet implements Serializable
{
	private static final long serialVersionUID = 1L;
	private final transient String text;
	private final transient FontType fontType;

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
