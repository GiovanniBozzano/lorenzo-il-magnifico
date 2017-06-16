package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketGameUpdateOtherTurn extends Packet
{
	private final GameInformations gameInformations;
	private final List<PlayerInformations> playersInformations;
	private final int turnPlayerIndex;

	public PacketGameUpdateOtherTurn(GameInformations gameInformations, List<PlayerInformations> playersInformations, int turnPlayerIndex)
	{
		super(PacketType.GAME_UPDATE_OTHER_TURN);
		this.gameInformations = gameInformations;
		this.playersInformations = new ArrayList<>(playersInformations);
		this.turnPlayerIndex = turnPlayerIndex;
	}

	public GameInformations getGameInformations()
	{
		return this.gameInformations;
	}

	public List<PlayerInformations> getPlayersInformations()
	{
		return this.playersInformations;
	}

	public int getTurnPlayerIndex()
	{
		return this.turnPlayerIndex;
	}
}
