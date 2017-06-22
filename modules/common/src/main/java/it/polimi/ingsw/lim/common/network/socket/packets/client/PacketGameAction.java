package it.polimi.ingsw.lim.common.network.socket.packets.client;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.actions.ActionInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameAction extends Packet
{
	private final ActionInformations action;

	public PacketGameAction(ActionInformations action)
	{
		super(PacketType.GAME_ACTION);
		this.action = action;
	}

	public ActionInformations getAction()
	{
		return this.action;
	}
}
