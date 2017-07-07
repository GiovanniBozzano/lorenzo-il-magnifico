package it.polimi.ingsw.lim.client.game.player;

import it.polimi.ingsw.lim.common.enums.*;

import java.util.*;

public class PlayerData
{
	private final String username;
	private final Color color;
	private final Map<CardType, List<Integer>> developmentCards = new EnumMap<>(CardType.class);
	private final Map<Integer, Boolean> leaderCardsPlayed = new HashMap<>();
	private final Map<ResourceType, Integer> resourceAmounts = new EnumMap<>(ResourceType.class);
	private final Map<FamilyMemberType, BoardPosition> familyMembersPositions = new EnumMap<>(FamilyMemberType.class);
	private int personalBonusTile;
	private int leaderCardsInHandNumber;

	public PlayerData(String username, Color color)
	{
		this.username = username;
		this.color = color;
		this.developmentCards.put(CardType.BUILDING, new ArrayList<>());
		this.developmentCards.put(CardType.CHARACTER, new ArrayList<>());
		this.developmentCards.put(CardType.TERRITORY, new ArrayList<>());
		this.developmentCards.put(CardType.VENTURE, new ArrayList<>());
	}

	public void setDevelopmentCards(CardType cardType, List<Integer> developmentCards)
	{
		this.developmentCards.get(cardType).clear();
		this.developmentCards.get(cardType).addAll(developmentCards);
	}

	public String getUsername()
	{
		return this.username;
	}

	public Color getColor()
	{
		return this.color;
	}

	public int getPersonalBonusTile()
	{
		return this.personalBonusTile;
	}

	public void setPersonalBonusTile(int personalBonusTile)
	{
		this.personalBonusTile = personalBonusTile;
	}

	public Map<CardType, List<Integer>> getDevelopmentCards()
	{
		return this.developmentCards;
	}

	public Map<Integer, Boolean> getLeaderCardsPlayed()
	{
		return this.leaderCardsPlayed;
	}

	public void setLeaderCardsPlayed(Map<Integer, Boolean> leaderCardsStatuses)
	{
		this.leaderCardsPlayed.clear();
		this.leaderCardsPlayed.putAll(leaderCardsStatuses);
	}

	public int getLeaderCardsInHandNumber()
	{
		return this.leaderCardsInHandNumber;
	}

	public void setLeaderCardsInHandNumber(int leaderCardsInHandNumber)
	{
		this.leaderCardsInHandNumber = leaderCardsInHandNumber;
	}

	public Map<ResourceType, Integer> getResourceAmounts()
	{
		return this.resourceAmounts;
	}

	public void setResourceAmounts(Map<ResourceType, Integer> resourceAmounts)
	{
		this.resourceAmounts.clear();
		this.resourceAmounts.putAll(resourceAmounts);
	}

	public Map<FamilyMemberType, BoardPosition> getFamilyMemberTypesPositions()
	{
		return this.familyMembersPositions;
	}

	public void setFamilyMemberTypesPositions(Map<FamilyMemberType, BoardPosition> familyMembersPositions)
	{
		this.familyMembersPositions.clear();
		this.familyMembersPositions.putAll(familyMembersPositions);
	}
}
