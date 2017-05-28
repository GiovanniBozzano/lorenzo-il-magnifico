package it.polimi.ingsw.lim.server.game;

import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.server.network.Connection;

import java.util.EnumMap;
import java.util.Map;

public class GameHandler
{
	private final Room room;
	private final Map<FamilyMemberType, Integer> familyMemberTypeValues = new EnumMap<>(FamilyMemberType.class);
	private Connection turnPlayer;

	GameHandler(Room room)
	{
		this.room = room;
	}

	public Map<FamilyMemberType, Integer> getFamilyMemberTypeValues()
	{
		return this.familyMemberTypeValues;
	}

	public Connection getTurnPlayer()
	{
		return this.turnPlayer;
	}
}
