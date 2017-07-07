package it.polimi.ingsw.lim.client.game.player;

import it.polimi.ingsw.lim.common.enums.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class PlayerDataTest
{
	private PlayerData playerData;

	@Before
	public void setUp()
	{
		this.playerData = new PlayerData("test", Color.BLUE);
		this.playerData.setPersonalBonusTile(1);
		this.playerData.setDevelopmentCards(CardType.BUILDING, Collections.singletonList(1));
		this.playerData.setDevelopmentCards(CardType.CHARACTER, Collections.singletonList(1));
		Map<Integer, Boolean> leaderCardsPlayed = new HashMap<>();
		leaderCardsPlayed.put(1, true);
		this.playerData.setLeaderCardsPlayed(leaderCardsPlayed);
		this.playerData.setLeaderCardsInHandNumber(4);
		Map<ResourceType, Integer> resourceAmounts = new EnumMap<>(ResourceType.class);
		resourceAmounts.put(ResourceType.COIN, 10);
		this.playerData.setResourceAmounts(resourceAmounts);
		Map<FamilyMemberType, BoardPosition> familyMemberTypesBoardPositions = new EnumMap<>(FamilyMemberType.class);
		familyMemberTypesBoardPositions.put(FamilyMemberType.BLACK, BoardPosition.COUNCIL_PALACE);
		this.playerData.setFamilyMemberTypesPositions(familyMemberTypesBoardPositions);
	}

	@Test
	public void testGetUsername()
	{
		Assert.assertEquals("test", this.playerData.getUsername());
	}

	@Test
	public void testGetColor()
	{
		Assert.assertEquals(Color.BLUE, this.playerData.getColor());
	}

	@Test
	public void testGetPersonalBonusTile()
	{
		Assert.assertEquals(1, this.playerData.getPersonalBonusTile());
	}

	@Test
	public void testGetDevelopmentCards()
	{
		Assert.assertFalse(this.playerData.getDevelopmentCards().get(CardType.BUILDING).isEmpty());
		Assert.assertFalse(this.playerData.getDevelopmentCards().get(CardType.CHARACTER).isEmpty());
		Assert.assertTrue(this.playerData.getDevelopmentCards().get(CardType.TERRITORY).isEmpty());
		Assert.assertTrue(this.playerData.getDevelopmentCards().get(CardType.VENTURE).isEmpty());
	}

	@Test
	public void testGetLeaderCardsPlayed()
	{
		Assert.assertTrue(this.playerData.getLeaderCardsPlayed().get(1));
	}

	@Test
	public void testGetLeaderCardsInHandNumber()
	{
		Assert.assertEquals(4, this.playerData.getLeaderCardsInHandNumber());
	}

	@Test
	public void testGetResourceAmounts()
	{
		Assert.assertEquals(10, (int) this.playerData.getResourceAmounts().get(ResourceType.COIN));
	}

	@Test
	public void testGetFamilyMembersPositions()
	{
		Assert.assertEquals(BoardPosition.COUNCIL_PALACE, this.playerData.getFamilyMemberTypesPositions().get(FamilyMemberType.BLACK));
	}
}
