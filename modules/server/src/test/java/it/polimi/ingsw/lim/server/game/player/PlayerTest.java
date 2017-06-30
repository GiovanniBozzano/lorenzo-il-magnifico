package it.polimi.ingsw.lim.server.game.player;

import it.polimi.ingsw.lim.common.enums.*;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest
{
	private Player player;

	@Before
	public void setUp()
	{
		this.player = new Player(new ConnectionRMI(null, null), new Room(RoomType.NORMAL), 0);
		this.player.getFamilyMembersPositions().put(FamilyMemberType.NEUTRAL, BoardPosition.HARVEST_SMALL);
		this.player.getPlayerResourceHandler().getResources().put(ResourceType.MILITARY_POINT, Player.getTerritorySlotsConditions().get(5));
		this.player.getPlayerResourceHandler().getResources().put(ResourceType.FAITH_POINT, Player.getExcommunicationConditions().get(Period.FIRST));
		this.player.getPlayerResourceHandler().addResource(ResourceType.COIN, 5);
		this.player.getPlayerResourceHandler().addResource(ResourceType.SERVANT, 2);
		this.player.getPlayerResourceHandler().addResource(ResourceType.STONE, 3);
		this.player.getPlayerResourceHandler().addResource(ResourceType.WOOD, 3);
	}

	@Test
	public void testIsOccupyingPosition()
	{
		Assert.assertTrue(this.player.isOccupyingBoardPosition(BoardPosition.HARVEST_SMALL));
	}

	@Test
	public void testIsTerritorySlotAvailable()
	{
		Assert.assertTrue(this.player.isTerritorySlotAvailable(5));
	}

	@Test
	public void testIsExcommunicated()
	{
		Assert.assertFalse(this.player.isExcommunicated(Period.FIRST));
	}

	@Test
	public void testConvertToVictoryPoints()
	{
		this.player.convertToVictoryPoints(true, true, true);
		Assert.assertEquals(4, (int) this.player.getPlayerResourceHandler().getResources().get(ResourceType.VICTORY_POINT));
	}

	@After
	public void tearDown()
	{
	}
}
