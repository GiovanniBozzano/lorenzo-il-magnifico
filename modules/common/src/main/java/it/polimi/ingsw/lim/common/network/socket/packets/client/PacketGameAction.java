package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformation;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameAction extends Packet
{
	private final ActionInformation action;

	public PacketGameAction(ActionInformation action)
	{
		super(PacketType.GAME_ACTION);
		this.action = action;
	}

	public ActionInformation getAction()
	{
		return this.action;
	}
}
