package it.polimi.ingsw.lim.client.game.player;

import it.polimi.ingsw.lim.common.enums.BoardPosition;
import it.polimi.ingsw.lim.common.enums.Color;
import it.polimi.ingsw.lim.common.enums.FamilyMemberType;
import it.polimi.ingsw.lim.common.enums.ResourceType;

import java.util.*;

public class PlayerData
{
	private final String username;
	private final Color color;
	private final List<Integer> developmentCardsBuilding = new ArrayList<>();
	private final List<Integer> developmentCardsCharacter = new ArrayList<>();
	private final List<Integer> developmentCardsTerritory = new ArrayList<>();
	private final List<Integer> developmentCardsVenture = new ArrayList<>();
	private final Map<Integer, Boolean> leaderCardsPlayed = new HashMap<>();
	private int leaderCardsInHandNumber;
	private final Map<ResourceType, Integer> resourceAmounts = new EnumMap<>(ResourceType.class);
	private final Map<FamilyMemberType, BoardPosition> familyMembersPositions = new EnumMap<>(FamilyMemberType.class);

	public PlayerData(String username, Color color)
	{
		this.username = username;
		this.color = color;
	}

	public String getUsername()
	{
		return this.username;
	}

	public Color getColor()
	{
		return this.color;
	}

	public List<Integer> getDevelopmentCardsBuilding()
	{
		return this.developmentCardsBuilding;
	}

	public void setDevelopmentCardsBuilding(List<Integer> developmentCardsBuilding)
	{
		this.developmentCardsBuilding.clear();
		this.developmentCardsBuilding.addAll(developmentCardsBuilding);
	}

	public List<Integer> getDevelopmentCardsCharacter()
	{
		return this.developmentCardsCharacter;
	}

	public void setDevelopmentCardsCharacter(List<Integer> developmentCardsCharacter)
	{
		this.developmentCardsCharacter.clear();
		this.developmentCardsCharacter.addAll(developmentCardsCharacter);
	}

	public List<Integer> getDevelopmentCardsTerritory()
	{
		return this.developmentCardsTerritory;
	}

	public void setDevelopmentCardsTerritory(List<Integer> developmentCardsTerritory)
	{
		this.developmentCardsTerritory.clear();
		this.developmentCardsTerritory.addAll(developmentCardsTerritory);
	}

	public List<Integer> getDevelopmentCardsVenture()
	{
		return this.developmentCardsVenture;
	}

	public void setDevelopmentCardsVenture(List<Integer> developmentCardsVenture)
	{
		this.developmentCardsVenture.clear();
		this.developmentCardsVenture.addAll(developmentCardsVenture);
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

	public Map<FamilyMemberType, BoardPosition> getFamilyMembersPositions()
	{
		return this.familyMembersPositions;
	}

	public void setFamilyMembersPositions(Map<FamilyMemberType, BoardPosition> familyMembersPositions)
	{
		this.familyMembersPositions.clear();
		this.familyMembersPositions.putAll(familyMembersPositions);
	}
}
