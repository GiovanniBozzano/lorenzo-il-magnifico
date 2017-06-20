package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.GameInformations;
import it.polimi.ingsw.lim.common.game.actions.AvailableAction;
import it.polimi.ingsw.lim.common.game.player.PlayerInformations;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PacketGameUpdate extends Packet
{
	private final GameInformations gameInformations;
	private final List<PlayerInformations> playersInformations;
	private final List<Integer> ownLeaderCardsHand;
	private final Map<ActionType, List<AvailableAction>> availableActions;

	public PacketGameUpdate(GameInformations gameInformations, List<PlayerInformations> playersInformations, List<Integer> ownLeaderCardsHand, Map<ActionType, List<AvailableAction>> availableActions)
	{
		super(PacketType.GAME_UPDATE);
		this.gameInformations = gameInformations;
		this.playersInformations = new ArrayList<>(playersInformations);
		this.ownLeaderCardsHand = new ArrayList<>(ownLeaderCardsHand);
		this.availableActions = new EnumMap<>(availableActions);
	}

	public GameInformations getGameInformations()
	{
		return this.gameInformations;
	}

	public List<PlayerInformations> getPlayersInformations()
	{
		return this.playersInformations;
	}

	public List<Integer> getOwnLeaderCardsHand()
	{
		return this.ownLeaderCardsHand;
	}

	public Map<ActionType, List<AvailableAction>> getAvailableActions()
	{
		return this.availableActions;
	}
}
