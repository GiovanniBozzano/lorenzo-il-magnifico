package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.PlayerInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.List;

public class PacketGameUpdate extends Packet
{
	private final boolean ownTurn;
	private final GameInformations gameInformations;
	private final List<PlayerInformations> playersInformations;
	private final List<ActionType> availableActions;

	public PacketGameUpdate(boolean ownTurn, GameInformations gameInformations, List<PlayerInformations> playersInformations, List<ActionType> availableActions)
	{
		super(PacketType.GAME_UPDATE);
		this.ownTurn = ownTurn;
		this.gameInformations = gameInformations;
		this.playersInformations = new ArrayList<>(playersInformations);
		this.availableActions = availableActions;
	}

	public boolean isOwnTurn()
	{
		return this.ownTurn;
	}

	public GameInformations getGameInformations()
	{
		return this.gameInformations;
	}

	public List<PlayerInformations> getPlayersInformations()
	{
		return this.playersInformations;
	}

	public List<ActionType> getAvailableActions()
	{
		return this.availableActions;
	}
}
