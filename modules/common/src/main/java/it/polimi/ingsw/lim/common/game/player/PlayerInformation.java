package it.polimi.ingsw.lim.common.game.player;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.CardType;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerInformation implements Serializable
{
	private final int index;
	private final int personalBonusTile;
	private final Map<CardType, List<Integer>> developmentCards;
	private final Map<Integer, Boolean> leaderCardsPlayed;
	private final int leaderCardsInHandNumber;
	private final Map<ResourceType, Integer> resourceAmounts;
	private final Map<FamilyMemberType, BoardPosition> familyMembersPositions;

	public PlayerInformation(int index, int personalBonusTile, Map<CardType, List<Integer>> developmentCards, Map<Integer, Boolean> leaderCardsPlayed, int leaderCardsInHandNumber, Map<ResourceType, Integer> resourceAmounts, Map<FamilyMemberType, BoardPosition> familyMembersPositions)
	{
		this.index = index;
		this.personalBonusTile = personalBonusTile;
		this.developmentCards = new EnumMap<>(developmentCards);
		this.leaderCardsPlayed = new HashMap<>(leaderCardsPlayed);
		this.leaderCardsInHandNumber = leaderCardsInHandNumber;
		this.resourceAmounts = new EnumMap<>(resourceAmounts);
		this.familyMembersPositions = new EnumMap<>(familyMembersPositions);
	}

	public int getIndex()
	{
		return this.index;
	}

	public int getPersonalBonusTile()
	{
		return this.personalBonusTile;
	}

	public Map<CardType, List<Integer>> getDevelopmentCards()
	{
		return this.developmentCards;
	}

	public Map<Integer, Boolean> getLeaderCardsPlayed()
	{
		return this.leaderCardsPlayed;
	}

	public int getLeaderCardsInHandNumber()
	{
		return this.leaderCardsInHandNumber;
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
