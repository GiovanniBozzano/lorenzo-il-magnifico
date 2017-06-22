package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

public class PacketGameExcommunicationChoiceRequest extends Packet
{
	private final Period period;

	public PacketGameExcommunicationChoiceRequest(Period period)
	{
		super(PacketType.GAME_EXCOMMUNICATION_CHOICE_REQUEST);
		this.period = period;
	}

	public Period getPeriod()
	{
		return this.period;
	}
}
