package it.polimi.ingsw.lim.common.game.player;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardStatus;

import java.io.Serializable;
import java.util.*;

public class PlayerInformations implements Serializable
{
	private final int index;
	private final List<Integer> developmentCardsBuilding;
	private final List<Integer> developmentCardsCharacter;
	private final List<Integer> developmentCardsTerritory;
	private final List<Integer> developmentCardsVenture;
	private final Map<Integer, LeaderCardStatus> leaderCardsStatuses;
	private final Map<ResourceType, Integer> resourceAmounts;
	private final Map<FamilyMemberType, BoardPosition> familyMembersPositions;

	public PlayerInformations(int index, List<Integer> developmentCardsBuilding, List<Integer> developmentCardsCharacter, List<Integer> developmentCardsTerritory, List<Integer> developmentCardsVenture, Map<Integer, LeaderCardStatus> leaderCardsStatuses, Map<ResourceType, Integer> resourceAmounts, Map<FamilyMemberType, BoardPosition> familyMembersPositions)
	{
		this.index = index;
		this.developmentCardsBuilding = new LinkedList<>(developmentCardsBuilding);
		this.developmentCardsCharacter = new LinkedList<>(developmentCardsCharacter);
		this.developmentCardsTerritory = new LinkedList<>(developmentCardsTerritory);
		this.developmentCardsVenture = new LinkedList<>(developmentCardsVenture);
		this.leaderCardsStatuses = new HashMap<>(leaderCardsStatuses);
		this.resourceAmounts = new EnumMap<>(resourceAmounts);
		this.familyMembersPositions = new EnumMap<>(familyMembersPositions);
	}

	public int getIndex()
	{
		return this.index;
	}

	public List<Integer> getDevelopmentCardsBuilding()
	{
		return this.developmentCardsBuilding;
	}

	public List<Integer> getDevelopmentCardsCharacter()
	{
		return this.developmentCardsCharacter;
	}

	public List<Integer> getDevelopmentCardsTerritory()
	{
		return this.developmentCardsTerritory;
	}

	public List<Integer> getDevelopmentCardsVenture()
	{
		return this.developmentCardsVenture;
	}

	public Map<Integer, LeaderCardStatus> getLeaderCardsStatuses()
	{
		return this.leaderCardsStatuses;
	}

	public Map<ResourceType, Integer> getResourceAmounts()
	{
		return this.resourceAmounts;
	}

	public Map<FamilyMemberType, BoardPosition> getFamilyMembersPositions()
	{
		return this.familyMembersPositions;
	}
}
