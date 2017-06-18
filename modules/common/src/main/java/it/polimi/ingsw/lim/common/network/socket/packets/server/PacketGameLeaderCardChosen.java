package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameLeaderCardChosen extends Packet
{
	private final int choicePlayerIndex;
	private final boolean closeDialog;

	public PacketGameLeaderCardChosen(int choicePlayerIndex, boolean closeDialog)
	{
		super(PacketType.GAME_LEADER_CARD_CHOSEN);
		this.choicePlayerIndex = choicePlayerIndex;
		this.closeDialog = closeDialog;
	}

	public int getChoicePlayerIndex()
	{
		return this.choicePlayerIndex;
	}

	public boolean isCloseDialog()
	{
		return this.closeDialog;
	}
}
