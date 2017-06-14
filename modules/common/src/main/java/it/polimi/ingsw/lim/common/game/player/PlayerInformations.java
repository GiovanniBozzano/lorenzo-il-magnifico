package it.polimi.ingsw.lim.common.game.player;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;
import it.polimi.ingsw.lim.common.game.cards.LeaderCardStatus;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PlayerInformations implements Serializable
{
	private final int index;
	private final List<Integer> developmentCardsBuilding;
	private final List<Integer> developmentCardsCharacter;
	private final List<Integer> developmentCardsTerritory;
	private final List<Integer> developmentCardsVenture;
	private final List<LeaderCardStatus> cardsLeaderStatuses;
	private final Map<ResourceType, Integer> resourceAmounts;
	private final Map<FamilyMemberType, BoardPosition> familyMembersPositions;

	public PlayerInformations(int index, List<Integer> developmentCardsBuilding, List<Integer> developmentCardsCharacter, List<Integer> developmentCardsTerritory, List<Integer> developmentCardsVenture, List<LeaderCardStatus> cardsLeaderStatuses, Map<ResourceType, Integer> resourceAmounts, Map<FamilyMemberType, BoardPosition> familyMembersPositions)
	{
		this.index = index;
		this.developmentCardsBuilding = new LinkedList<>(developmentCardsBuilding);
		this.developmentCardsCharacter = new LinkedList<>(developmentCardsCharacter);
		this.developmentCardsTerritory = new LinkedList<>(developmentCardsTerritory);
		this.developmentCardsVenture = new LinkedList<>(developmentCardsVenture);
		this.cardsLeaderStatuses = new LinkedList<>(cardsLeaderStatuses);
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

	public List<LeaderCardStatus> getCardsLeaderStatuses()
	{
		return this.cardsLeaderStatuses;
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
