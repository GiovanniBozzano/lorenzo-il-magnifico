package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;

import java.util.HashMap;
import java.util.Map;

public class PlayerInformations
{
	private final PlayerCardHandler playerCardHandler = new PlayerCardHandler();
	private final PlayerResourceHandler playerResourceHandler;
	private final Map<FamilyMemberType, ActionType> familyMembersPositions = new HashMap<>();

	public PlayerInformations(PlayerResourceHandler playerResourceHandler)
	{
		this.playerResourceHandler = playerResourceHandler;
		this.familyMembersPositions.put(FamilyMemberType.BLACK, ActionType.BOARD);
		this.familyMembersPositions.put(FamilyMemberType.ORANGE, ActionType.BOARD);
		this.familyMembersPositions.put(FamilyMemberType.WHITE, ActionType.BOARD);
	}

	public PlayerCardHandler getPlayerCardHandler()
	{
		return this.playerCardHandler;
	}

	public PlayerResourceHandler getPlayerResourceHandler()
	{
		return this.playerResourceHandler;
	}

	public Map<FamilyMemberType, ActionType> getFamilyMembersPositions()
	{
		return this.familyMembersPositions;
	}
}
