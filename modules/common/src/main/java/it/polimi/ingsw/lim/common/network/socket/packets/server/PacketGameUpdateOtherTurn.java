package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.game.GameInformation;
import it.polimi.ingsw.lim.common.game.player.PlayerInformation;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacketGameUpdateOtherTurn extends Packet
{
	private final GameInformation gameInformation;
	private final List<PlayerInformation> playersInformation;
	private final Map<Integer, Boolean> ownLeaderCardsHand;
	private final int turnPlayerIndex;

	public PacketGameUpdateOtherTurn(GameInformation gameInformation, List<PlayerInformation> playersInformation, Map<Integer, Boolean> ownLeaderCardsHand, int turnPlayerIndex)
	{
		super(PacketType.GAME_UPDATE_OTHER_TURN);
		this.gameInformation = gameInformation;
		this.playersInformation = new ArrayList<>(playersInformation);
		this.ownLeaderCardsHand = new HashMap<>(ownLeaderCardsHand);
		this.turnPlayerIndex = turnPlayerIndex;
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

	public int getTurnPlayerIndex()
	{
		return this.turnPlayerIndex;
	}
}
