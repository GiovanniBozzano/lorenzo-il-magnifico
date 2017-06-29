package it.polimi.ingsw.lim.common.network.socket.packets.server;

import it.polimi.ingsw.lim.common.enums.PacketType;
import it.polimi.ingsw.lim.common.enums.Period;
import it.polimi.ingsw.lim.common.game.player.PlayerIdentification;
import it.polimi.ingsw.lim.common.game.utils.ResourceAmount;
import it.polimi.ingsw.lim.common.network.socket.packets.Packet;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PacketGameStarted extends Packet
{
	private final Map<Period, Integer> excommunicationTiles;
	private final Map<Integer, List<ResourceAmount>> councilPrivilegeRewards;
	private final Map<Integer, PlayerIdentification> playersIdentifications;
	private final int ownPlayerIndex;

	public PacketGameStarted(Map<Period, Integer> excommunicationTiles, Map<Integer, List<ResourceAmount>> councilPrivilegeRewards, Map<Integer, PlayerIdentification> playersIdentifications, int ownPlayerIndex)
	{
		super(PacketType.GAME_STARTED);
		this.excommunicationTiles = new EnumMap<>(excommunicationTiles);
		this.councilPrivilegeRewards = new HashMap<>(councilPrivilegeRewards);
		this.playersIdentifications = new HashMap<>(playersIdentifications);
		this.ownPlayerIndex = ownPlayerIndex;
	}

	public Map<Period, Integer> getExcommunicationTiles()
	{
		return this.excommunicationTiles;
	}

	public Map<Integer, List<ResourceAmount>> getCouncilPrivilegeRewards()
	{
		return this.councilPrivilegeRewards;
	}

	public Map<Integer, PlayerIdentification> getPlayersIdentifications()
	{
		return this.playersIdentifications;
	}

	public int getOwnPlayerIndex()
	{
		return this.ownPlayerIndex;
	}
}
