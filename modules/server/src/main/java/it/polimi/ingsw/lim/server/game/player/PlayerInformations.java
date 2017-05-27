package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.bonus.Bonus;
import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.events.IEvent;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class PlayerInformations
{
	private final PlayerCardHandler playerCardHandler = new PlayerCardHandler(this);
	private final PlayerResourceHandler playerResourceHandler;
	private final Map<FamilyMemberType, BoardPosition> familyMembersPositions = new EnumMap<>(FamilyMemberType.class);
	private final List<Bonus<? extends IEvent>> activeBonuses = new ArrayList<>();

	public PlayerInformations(PlayerResourceHandler playerResourceHandler)
	{
		this.playerResourceHandler = playerResourceHandler;
		this.familyMembersPositions.put(FamilyMemberType.BLACK, BoardPosition.NONE);
		this.familyMembersPositions.put(FamilyMemberType.ORANGE, BoardPosition.NONE);
		this.familyMembersPositions.put(FamilyMemberType.WHITE, BoardPosition.NONE);
		this.familyMembersPositions.put(FamilyMemberType.NEUTRAL, BoardPosition.NONE);
	}

	public PlayerCardHandler getPlayerCardHandler()
	{
		return this.playerCardHandler;
	}

	public PlayerResourceHandler getPlayerResourceHandler()
	{
		return this.playerResourceHandler;
	}

	public Map<FamilyMemberType, BoardPosition> getFamilyMembersPositions()
	{
		return this.familyMembersPositions;
	}

	public List<Bonus<? extends IEvent>> getActiveBonuses()
	{
		return this.activeBonuses;
	}
}
