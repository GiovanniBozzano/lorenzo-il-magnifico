package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.io.Serializable;
import java.util.*;

public class PacketGameUpdate extends Packet
{
	private final GameInformation gameInformation;
	private final List<PlayerInformation> playersInformation;
	private final Map<Integer, Boolean> ownLeaderCardsHand;
	private final Map<ActionType, List<Serializable>> availableActions;

	public PacketGameUpdate(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, Map<ActionType, List<Serializable>> availableActions)
	{
		super(PacketType.GAME_UPDATE);
		this.gameInformation = gameInformation;
		this.playersInformation = new ArrayList<>(playersInformation);
		this.ownLeaderCardsHand = new HashMap<>(ownLeaderCardsHand);
		this.availableActions = new EnumMap<>(availableActions);
	}

	public GameInformation getGameInformation()
	{
		return this.gameInformation;
	}

	public List<PlayerInformation> getPlayersInformation()
	{
		return this.playersInformation;
	}

	public Map<Integer, Boolean> getOwnLeaderCardsHand()
	{
		return this.ownLeaderCardsHand;
	}

	public Map<ActionType, List<Serializable>> getAvailableActions()
	{
		return this.availableActions;
	}
}
