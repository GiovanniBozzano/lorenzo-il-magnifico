package it.polimi.ingsw.lim.server.game.actions;

import it.polimi.ingsw.lim.common.enums.ActionType;
import it.polimi.ingsw.lim.common.enums.RoomType;
import it.polimi.ingsw.lim.common.exceptions.GameActionFailedException;
import it.polimi.ingsw.lim.common.utils.DebuggerFormatter;
import it.polimi.ingsw.lim.server.Server;
import it.polimi.ingsw.lim.server.game.GameHandler;
import it.polimi.ingsw.lim.server.game.Room;
import it.polimi.ingsw.lim.server.game.player.Player;
import it.polimi.ingsw.lim.server.network.rmi.ConnectionRMI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class ActionRefuseRewardTest
{
	private ActionRefuseReward actionRefuseReward;

	@Before
	public void setUp()
	{
		Server.setDebugger(Logger.getLogger(Server.class.getSimpleName().toUpperCase()));
		Server.getDebugger().setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new DebuggerFormatter());
		Server.getDebugger().addHandler(consoleHandler);
		Server.setInstance(new Server());
		Room room = new Room(RoomType.NORMAL);
		GameHandler gameHandler = new GameHandler(room);
		room.setGameHandler(gameHandler);
		gameHandler.setExpectedAction(ActionType.CHOOSE_REWARD_PICK_DEVELOPMENT_CARD);
		gameHandler.setTimerExecutor(Executors.newSingleThreadScheduledExecutor());
		Player player = new Player(new ConnectionRMI(null, null), room, 0);
		gameHandler.getTurnOrder().add(player);
		gameHandler.setupRound();
		this.actionRefuseReward = new ActionRefuseReward(player);
	}

	@Test
	public void testIsLegal() throws GameActionFailedException
	{
		this.actionRefuseReward.isLegal();
	}

	@After
	public void tearDown()
	{
		Server.getInstance().stop();
	}
}
