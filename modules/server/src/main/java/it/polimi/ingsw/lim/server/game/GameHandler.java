package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.WorkSlotType;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.HashMap;
import java.util.Map;

public class GameHandler
{
	private final Room room;
	private final Map<FamilyMemberType, Integer> familyMemberTypeValues = new HashMap<>();
	private Connection turnPlayer;

	GameHandler(Room room)
	{
		this.room = room;
	}

	public Map<FamilyMemberType, Integer> getFamilyMemberTypeValues()
	{
		return this.familyMemberTypeValues;
	}

	public WorkSlotType getFreeHarvestSlotType()
	{
		for (Connection player : this.room.getPlayers()) {
			for (BoardPosition boardPosition : player.getPlayerInformations().getFamilyMembersPositions().values()) {
				if (boardPosition == BoardPosition.HARVEST_SMALL) {
					return WorkSlotType.BIG;
				}
			}
		}
		return WorkSlotType.SMALL;
	}

	public WorkSlotType getFreeProductionSlotType()
	{
		for (Connection player : this.room.getPlayers()) {
			for (BoardPosition boardPosition : player.getPlayerInformations().getFamilyMembersPositions().values()) {
				if (boardPosition == BoardPosition.PRODUCTION_SMALL) {
					return WorkSlotType.BIG;
				}
			}
		}
		return WorkSlotType.SMALL;
	}

	public Connection getTurnPlayer()
	{
		return this.turnPlayer;
	}
}
