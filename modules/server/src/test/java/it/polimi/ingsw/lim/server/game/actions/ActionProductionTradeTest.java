package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.Executors;

public class ActionProductionTradeTest
{
	private ActionProductionTrade actionProductionTrade;

	@Before
	public void setUp()
	{
		Server.setInstance(new Server());
		Room room = new Room(RoomType.NORMAL);
		GameHandler gameHandler = new GameHandler(room);
		Player player = new Player(new ConnectionRMI(null, null), room, 0);
		gameHandler.setTurnPlayer(player);
		gameHandler.setTimerExecutor(Executors.newSingleThreadScheduledExecutor());
		room.setGameHandler(gameHandler);
		this.actionProductionTrade = new ActionProductionTrade(new HashMap<>(), player);
	}

	@Test(expected = GameActionFailedException.class)
	public void testIsLegal() throws GameActionFailedException
	{
		this.actionProductionTrade.isLegal();
	}

	@Test(expected = GameActionFailedException.class)
	public void testApply() throws GameActionFailedException
	{
		this.actionProductionTrade.apply();
	}

	@After
	public void tearDown()
	{
	}
}
